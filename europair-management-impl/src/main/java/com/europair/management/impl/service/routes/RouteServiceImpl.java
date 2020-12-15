package com.europair.management.impl.service.routes;

import com.europair.management.api.dto.contribution.ContributionDTO;
import com.europair.management.api.dto.routes.RouteDto;
import com.europair.management.api.dto.routes.RouteExtendedDto;
import com.europair.management.api.dto.routes.RouteFrequencyDayDto;
import com.europair.management.api.enums.FrequencyEnum;
import com.europair.management.api.enums.RouteStatesEnum;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.common.service.IStateChangeService;
import com.europair.management.impl.mappers.contributions.IContributionMapper;
import com.europair.management.impl.mappers.routes.IRouteFrequencyDayMapper;
import com.europair.management.impl.mappers.routes.IRouteMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.airport.repository.AirportRepository;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.files.repository.FileRepository;
import com.europair.management.rest.model.flights.entity.Flight;
import com.europair.management.rest.model.flights.repository.FlightRepository;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.entity.RouteFrequencyDay;
import com.europair.management.rest.model.routes.repository.RouteFrequencyDayRepository;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class RouteServiceImpl implements IRouteService {

    private final String FILE_ID_FILTER = "file.id";
    private final String PARENT_ROUTE_FILTER = "parentRoute";

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private RouteFrequencyDayRepository routeFrequencyDayRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private IStateChangeService stateChangeService;

    @Override
    public Page<RouteExtendedDto> findAllPaginatedByFilter(final Long fileId, Pageable pageable, CoreCriteria criteria) {
        checkIfFileExists(fileId);
        Utils.addCriteriaIfNotExists(criteria, FILE_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(fileId));
        Utils.addCriteriaIfNotExists(criteria, PARENT_ROUTE_FILTER, OperatorEnum.IS_NULL, null);

        return routeRepository.findRouteByCriteria(criteria, pageable)
                .map(route -> {
                    RouteExtendedDto dto = IRouteMapper.INSTANCE.toExtendedDto(route);
                    // Map seating info from flights
                    if (!CollectionUtils.isEmpty(dto.getRotations())) {
                        dto.getRotationsExtended().forEach(rotation -> {
                            Route routeRotation = route.getRotations().stream()
                                    .filter(r -> r.getId().equals(rotation.getId())).findAny().orElse(null);
                            if (routeRotation != null) {
                                Flight flight = getAnyRotationFlight(routeRotation);
                                rotation.setSeatsC(flight == null ? null : flight.getSeatsC());
                                rotation.setSeatsF(flight == null ? null : flight.getSeatsF());
                                rotation.setSeatsY(flight == null ? null : flight.getSeatsY());
                            }
                        });
                        RouteExtendedDto firstRotation = dto.getRotationsExtended().get(0);
                        dto.setSeatsF(firstRotation.getSeatsF());
                        dto.setSeatsC(firstRotation.getSeatsC());
                        dto.setSeatsY(firstRotation.getSeatsY());
                    }

                    return dto;
                });
    }

    @Override
    public RouteDto findById(final Long fileId, Long id) {
        checkIfFileExists(fileId);
        return IRouteMapper.INSTANCE.toDto(routeRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.ROUTE_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    public RouteDto saveRoute(final Long fileId, RouteExtendedDto routeDto) {
        checkIfFileExists(fileId);
        if (routeDto.getId() != null) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.ROUTE_NEW_WITH_ID, String.valueOf(routeDto.getId()));
        }

        // Route validations
        routeValidations(routeDto);

        // Validate Route Airports
        List<String> iataCodes = Utils.mapRouteAirportIataCodes(routeDto.getLabel());
        Map<String, Airport> routeAirports = iataCodes.stream()
                .distinct()
                .map(iata -> airportRepository.findFirstByIataCode(iata)
                        .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRPORT_IATA_NOT_FOUND, iata)))
                .collect(Collectors.toMap(Airport::getIataCode, airport -> airport));

        Route route = IRouteMapper.INSTANCE.toEntity(routeDto);

        // Set relationships
        route.setFileId(fileId);

        // Default state
        route.setRouteState(RouteStatesEnum.SALES);

        // Persist entity
        route = routeRepository.save(route);

        // Save route frequency days
        if (!Collections.isEmpty(routeDto.getFrequencyDays())) {
            List<RouteFrequencyDay> frequencyDays = new ArrayList<>();
            for (RouteFrequencyDayDto rfd : routeDto.getFrequencyDays()) {
                RouteFrequencyDay frequencyDay = IRouteFrequencyDayMapper.INSTANCE.toEntity(rfd);
                frequencyDay.setRoute(route);
                frequencyDays.add(frequencyDay);
            }
            frequencyDays = routeFrequencyDayRepository.saveAll(frequencyDays);
            route.setFrequencyDays(frequencyDays);
        }

        // Calculate rotation dates and update rotation number
        List<LocalDate> rotationDates = new ArrayList<>();
        LocalDate auxDate = route.getStartDate();
        while (auxDate != null && auxDate.isBefore(routeDto.getEndDate().plusDays(1))) {
            auxDate = calculateRotationDate(auxDate, route, auxDate.equals(route.getStartDate()));
            if (auxDate != null) {
                rotationDates.add(auxDate);
                if (auxDate.equals(route.getStartDate())) {
                    auxDate = auxDate.plusDays(1);
                }
            }
        }
        route.setRotationsNumber(rotationDates.size());
        route = routeRepository.save(route);

        // Create rotations
        List<Route> rotations = createRotations(route, routeAirports, rotationDates, routeDto);
        route.setRotations(rotations);

        return IRouteMapper.INSTANCE.toDto(route);
    }

    @Override
    public RouteDto updateRoute(final Long fileId, Long id, RouteDto routeDto) {
        checkIfFileExists(fileId);
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.ROUTE_NOT_FOUND, String.valueOf(id)));
        IRouteMapper.INSTANCE.updateFromDto(routeDto, route);

        // Delete route frequency days
        routeFrequencyDayRepository.removeByRouteId(id);
        route.getFrequencyDays().clear();

        route = routeRepository.save(route);

        // Recreate route frequency days
        if (!Collections.isEmpty(routeDto.getFrequencyDays())) {
            List<RouteFrequencyDay> frequencyDays = new ArrayList<>();
            for (RouteFrequencyDayDto rfd : routeDto.getFrequencyDays()) {
                RouteFrequencyDay frequencyDay = IRouteFrequencyDayMapper.INSTANCE.toEntity(rfd);
                frequencyDay.setRoute(route);
                frequencyDays.add(frequencyDay);
            }
            frequencyDays = routeFrequencyDayRepository.saveAll(frequencyDays);
            route.setFrequencyDays(frequencyDays);
        }

        // ToDo: modificar rotaciones o recrearlas?

        return IRouteMapper.INSTANCE.toDto(route);
    }

    @Override
    public void deleteRoute(final Long fileId, Long id) {
        checkIfFileExists(fileId);
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.ROUTE_NOT_FOUND, String.valueOf(id)));
        if (!CollectionUtils.isEmpty(route.getContributions())) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.ROUTE_WITH_CONTRIBUTIONS);
        }
        routeRepository.deleteById(id);
    }

    private void checkIfFileExists(final Long fileId) {
        if (!fileRepository.existsById(fileId)) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FILE_NOT_FOUND, String.valueOf(fileId));
        }
    }

    // Route Rotations

    private List<Route> createRotations(@NotNull final Route parentRoute, final Map<String, Airport> routeAirportMap,
                                        final List<LocalDate> rotationDates, final RouteExtendedDto routeDto) {
        List<Route> rotations = rotationDates.stream().map(date -> {
            Route rotation = IRouteMapper.INSTANCE.mapRotation(parentRoute);
            // Set relationships
            rotation.setFileId(parentRoute.getFileId());
            rotation.setParentRouteId(parentRoute.getId());

            rotation.setStartDate(date);
            rotation.setEndDate(date);

            return rotation;
        }).collect(Collectors.toList());

        if (rotations.size() > 0) {
            rotations = routeRepository.saveAll(rotations);
            // Add flights
            rotations.forEach(rotation -> createFlights(rotation, routeAirportMap, routeDto));

        } else {
            rotations = null;
        }

        return rotations;
    }

    private LocalDate calculateRotationDate(LocalDate lastRotationDate, Route route, final boolean firstRotation) {
        LocalDate rotationDate = lastRotationDate != null ? lastRotationDate : route.getStartDate();

        if (route.getFrequency() == null || FrequencyEnum.ADHOC.equals(route.getFrequency())) {
            return firstRotation ? rotationDate : null;
        }

        final List<DayOfWeek> dayOfWeekList = route.getFrequencyDays().stream()
                .filter(rfd -> rfd.getWeekday() != null)
                .map(RouteFrequencyDay::getWeekday)
                .distinct()
                .collect(Collectors.toList());

        final List<Integer> dayOfMonthList = route.getFrequencyDays().stream()
                .filter(rfd -> rfd.getMonthDay() != null)
                .map(RouteFrequencyDay::getMonthDay)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        // If its the first rotation we search from the startDate, else we search from the next day from start
        switch (route.getFrequency()) {
            // ToDo: cambio de bucle para mejorar rendimiento??
            case DAILY:
            case WEEKLY:
                for (LocalDate date = firstRotation ? rotationDate : rotationDate.plusDays(1); date.isBefore(route.getEndDate().plusDays(1)); date = date.plusDays(1)) {
                    if (dayOfWeekList.contains(date.getDayOfWeek())) {
                        return date;
                    }
                }
                break;

            case BIWEEKLY:
                DayOfWeek min = dayOfWeekList.stream().min(Comparator.comparingInt(DayOfWeek::getValue)).orElse(null);
                DayOfWeek max = dayOfWeekList.stream().max(Comparator.comparingInt(DayOfWeek::getValue))
                        .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.ROUTE_WITH_NO_FREQ_DAYS));

                LocalDate startCounter;
                if (firstRotation) {
                    // First rotation: same date
                    startCounter = rotationDate;
                } else if (max.equals(min)) {
                    // Single week day selected: Add two weeks (at start of week if not on selected weekday)
                    startCounter = rotationDate.getDayOfWeek().equals(max) ? rotationDate.plusWeeks(2) :
                            rotationDate.plusWeeks(2).minusDays(rotationDate.getDayOfWeek().getValue() - 1);
                } else {
                    // Multiple week days selected: Add two weeks if current date on max weekday, else add a day
                    startCounter = rotationDate.getDayOfWeek().equals(max) ?
                            rotationDate.plusWeeks(2).minusDays(rotationDate.getDayOfWeek().getValue() - 1) :
                            rotationDate.plusDays(1);
                }

                for (LocalDate date = startCounter; date.isBefore(route.getEndDate()); date = date.plusDays(1)) {
                    if (dayOfWeekList.contains(date.getDayOfWeek())) {
                        return date;
                    }
                }
                break;
            case DAY_OF_MONTH:
                Integer minDayOfMonth = dayOfMonthList.stream().min(Integer::compareTo)
                        .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.ROUTE_WITH_NO_FREQ_DAYS));

                if (firstRotation && dayOfMonthList.contains(rotationDate.getDayOfMonth())) {
                    return rotationDate;
                }

                LocalDate nextMonthDate = rotationDate;
                Integer nextMonthDay = dayOfMonthList.stream()
                        .filter(monthDay -> monthDay > rotationDate.getDayOfMonth())
                        .min(Integer::compareTo).orElse(null);

                if (nextMonthDay == null) {
                    nextMonthDay = minDayOfMonth;
                    nextMonthDate = nextMonthDate.plusMonths(1);
                }

                try {
                    nextMonthDate = LocalDate.of(nextMonthDate.getYear(), nextMonthDate.getMonth(), nextMonthDay);
                    if (nextMonthDate.isAfter(route.getEndDate())) {
                        return null;
                    } else {
                        return nextMonthDate;
                    }

                } catch (DateTimeException e) {
                    // Search again with the next valid date as last rotation date
                    LocalDate nextValidDate = getNextValidDate(nextMonthDate.getYear(), nextMonthDate.getMonth(), nextMonthDay);
                    return calculateRotationDate(nextValidDate, route, firstRotation);
//                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rotation date out of range (yyyy-MM-dd): " +
//                            nextMonthDate.getYear() + "-" + nextMonthDate.getMonth() + "-" + nextMonthDay);
                }
        }

        // Reached end of for loop, no more available dates
        return null;
    }

    @Override
    public RouteDto updateRouteRotation(Long routeId, Long id, RouteDto routeDto) {
        if (!routeRepository.existsById(routeId)) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.ROUTE_NOT_FOUND, String.valueOf(routeId));
        }
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.ROUTE_ROTATION_NOT_FOUND, String.valueOf(id)));
        IRouteMapper.INSTANCE.updateFromDto(routeDto, route);
        route = routeRepository.save(route);

        // ToDo: modificar vuelos??
        return IRouteMapper.INSTANCE.toDto(route);
    }

    @Override
    public void updateStates(Long fileId, List<Long> routeIds, RouteStatesEnum state) {
        checkIfFileExists(fileId);
        stateChangeService.changeState(routeIds, state);
    }

    @Override
    public List<String> getValidRouteStatesToChange(Long id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.ROUTE_NOT_FOUND, String.valueOf(id)));
        return Stream.of(RouteStatesEnum.values())
                .filter(state -> stateChangeService.canChangeState(route, state))
                .map(RouteStatesEnum::name)
                .collect(Collectors.toList());
    }

    // Flights
    private void createFlights(@NotNull final Route rotation, final Map<String, Airport> routeAirportMap, final RouteExtendedDto routeDto) {
        List<Pair<String, String>> iataFlightInfo = Utils.getRotationAirportsFlights(rotation);
        int[] auxOrder = {1};
        flightRepository.saveAll(iataFlightInfo.stream()
                .map(iataPair -> {
                    Airport origin = routeAirportMap.get(iataPair.getFirst());
                    Flight flight = new Flight();
                    flight.setOriginId(origin.getId());
                    flight.setDestinationId(routeAirportMap.get(iataPair.getSecond()).getId());
                    flight.setTimeZone(origin.getTimeZone());
                    flight.setDepartureTime((null != rotation.getStartDate() ? rotation.getStartDate().atStartOfDay() : null));
                    flight.setArrivalTime((null != rotation.getStartDate() ? rotation.getStartDate().atStartOfDay() : null));
                    flight.setRealDepartureTime(flight.getDepartureTime());
                    flight.setRealArrivalTime(flight.getArrivalTime());
                    flight.setRouteId(rotation.getId());
                    flight.setSeatsC(routeDto.getSeatsC());
                    flight.setSeatsF(routeDto.getSeatsF());
                    flight.setSeatsY(routeDto.getSeatsY());
                    flight.setOrder(auxOrder[0]);
                    auxOrder[0]++; // Increase order
                    return flight;
                }).collect(Collectors.toList())
        );
    }

    // Validations
    private void routeValidations(final RouteDto routeDto) {
        if (routeDto.getStartDate().isAfter(routeDto.getEndDate())) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.ROUTE_DATES_ERROR);
        }
    }

    /**
     * Route with Contributions
     *
     * @param idRoute
     * @return
     */
    public List<ContributionDTO> getContributionUsingRouteId(Long idRoute) {
        List<ContributionDTO> res = null;

        Route route = this.routeRepository.findById(idRoute).orElseThrow(() ->
                Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.ROUTE_NOT_FOUND, String.valueOf(idRoute)));
        Set<Contribution> contributions = route.getContributions();

        res = IContributionMapper.INSTANCE.toListDtos(contributions.stream()
                .filter(contribution -> contribution.getRemovedAt() == null)
                .collect(Collectors.toList()));

        return res;
    }

    private Flight getAnyRotationFlight(final Route route) {
        if (!CollectionUtils.isEmpty(route.getFlights())) {
            return route.getFlights().stream().findAny().orElse(null);
        } else {
            return null;
        }
    }

    /**
     * Return next valid date of the passed values
     *
     * @param year     Year value
     * @param month    Month value
     * @param monthDay Day of month value
     * @return Valid date
     */
    private LocalDate getNextValidDate(int year, Month month, int monthDay) {
        LocalDate validDate = null;
        while (validDate == null) {
            try {
                validDate = LocalDate.of(year, month, monthDay);
            } catch (DateTimeException e) {
                if (monthDay >= 31) {
                    // End of month: day 1 of next month
                    month = month.plus(1);
                    year = Month.JANUARY.equals(month) ? year + 1 : year;
                    monthDay = 1;
                } else {
                    monthDay++;
                }
            }
        }
        return validDate;
    }

}
