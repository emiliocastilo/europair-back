package com.europair.management.impl.service.routes;

import com.europair.management.api.dto.routes.RouteDto;
import com.europair.management.api.dto.routes.RouteFrequencyDayDto;
import com.europair.management.api.enums.FrequencyEnum;
import com.europair.management.impl.mappers.routes.IRouteFrequencyDayMapper;
import com.europair.management.impl.mappers.routes.IRouteMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.files.repository.FileRepository;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.entity.RouteFrequencyDay;
import com.europair.management.rest.model.routes.repository.RouteFrequencyDayRepository;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Page<RouteDto> findAllPaginatedByFilter(final Long fileId, Pageable pageable, CoreCriteria criteria) {
        checkIfFileExists(fileId);
        Utils.addCriteriaIfNotExists(criteria, FILE_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(fileId));
        Utils.addCriteriaIfNotExists(criteria, PARENT_ROUTE_FILTER, OperatorEnum.IS_NULL, null);

        return routeRepository.findRouteByCriteria(criteria, pageable)
                .map(IRouteMapper.INSTANCE::toDto);
    }

    @Override
    public RouteDto findById(final Long fileId, Long id) {
        checkIfFileExists(fileId);
        return IRouteMapper.INSTANCE.toDto(routeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + id)));
    }

    @Override
    public RouteDto saveRoute(final Long fileId, RouteDto routeDto) {
        checkIfFileExists(fileId);
        if (routeDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New Route expected. Identifier %s got", routeDto.getId()));
        }

        Route route = IRouteMapper.INSTANCE.toEntity(routeDto);

        // Set relationships
        File file = new File();
        file.setId(fileId);
        route.setFile(file);

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

        // Create rotations
        List<Route> rotations = createRotations(route);
        route.setRotations(rotations);

        return IRouteMapper.INSTANCE.toDto(route);
    }

    @Override
    public RouteDto updateRoute(final Long fileId, Long id, RouteDto routeDto) {
        checkIfFileExists(fileId);
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + id));
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
        if (!routeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + id);
        }
        routeRepository.deleteById(id);
    }

    private void checkIfFileExists(final Long fileId) {
        if (!fileRepository.existsById(fileId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found with id: " + fileId);
        }
    }

    // Route Rotations

    private List<Route> createRotations(@NotNull final Route parentRoute) {
        List<Route> rotations = new ArrayList<>();

        LocalDate auxDate = parentRoute.getStartDate();
        for (int i = 0; i < parentRoute.getRotationsNumber(); i++) {
            Route newRotation = IRouteMapper.INSTANCE.mapRotation(parentRoute);
            // Set relationships
            newRotation.setFile(parentRoute.getFile());
            newRotation.setParentRoute(parentRoute);

            auxDate = calculateRotationDate(auxDate, parentRoute, i == 0);
            newRotation.setStartDate(auxDate);
            newRotation.setEndDate(auxDate);

            rotations.add(newRotation);
        }

        return rotations.size() > 0 ? routeRepository.saveAll(rotations) : null;
    }

    private LocalDate calculateRotationDate(LocalDate lastRotationDate, Route route, final boolean firstRotation) {
        LocalDate rotationDate = lastRotationDate != null ? lastRotationDate : route.getStartDate();

        if (route.getFrequency() == null || FrequencyEnum.ADHOC.equals(route.getFrequency())) {
            return rotationDate;
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
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Route frequency days found!"));

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
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Route frequency days found!"));

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
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rotation date after end date: " + nextMonthDate);
                    } else {
                        return nextMonthDate;
                    }

                } catch (DateTimeException e) {
                    // ToDo: lanzar exception o seleccionar otro día??
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rotation date out of range (yyyy-MM-dd): " +
                            nextMonthDate.getYear() + "-" + nextMonthDate.getMonth() + "-" + nextMonthDay);
                }
        }

        return rotationDate;
    }

    @Override
    public RouteDto updateRouteRotation(Long routeId, Long id, RouteDto routeDto) {
        if (!routeRepository.existsById(routeId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId);
        }
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rotation not found with id: " + id));
        IRouteMapper.INSTANCE.updateFromDto(routeDto, route);
        route = routeRepository.save(route);

        // ToDo: modificar vuelos??
        return IRouteMapper.INSTANCE.toDto(route);
    }
}
