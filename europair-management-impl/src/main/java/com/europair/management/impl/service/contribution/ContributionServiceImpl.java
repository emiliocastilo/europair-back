package com.europair.management.impl.service.contribution;

import com.europair.management.api.dto.contribution.ContributionDTO;
import com.europair.management.impl.mappers.contributions.IContributionMapper;
import com.europair.management.impl.service.conversions.ConversionService;
import com.europair.management.impl.service.flights.IFlightTaxService;
import com.europair.management.impl.util.DistanceSpeedUtils;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.contributions.repository.ContributionRepository;
import com.europair.management.rest.model.fleet.entity.AircraftType;
import com.europair.management.rest.model.flights.entity.Flight;
import com.europair.management.rest.model.flights.entity.FlightTax;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.entity.RouteAirport;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ContributionServiceImpl implements IContributionService {

    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private IFlightTaxService flightTaxService;

    @Autowired
    private ConversionService conversionService;

    @Override
    public Page<ContributionDTO> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return contributionRepository.findContributionByCriteria(criteria, pageable).map(IContributionMapper.INSTANCE::toDto);
    }

    @Override
    public ContributionDTO findById(Long id) {
        return IContributionMapper.INSTANCE.toDto(contributionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found with id: " + id)));
    }

    @Override
    @Transactional(readOnly = false)
    public ContributionDTO saveContribution(ContributionDTO contributionDTO) {
        if (contributionDTO.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New Contribution expected. Identifier %s got", contributionDTO.getId()));
        }

        Contribution contribution = IContributionMapper.INSTANCE.toEntity(contributionDTO);
        contribution = contributionRepository.save(contribution);

        // must activate flag in route to indicate the route has a contribution
        final Long routeId = contribution.getRouteId();
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId));
        route.setHasContributions(true);

        // Block hour
        setHourBlockValues(route, contribution);

        routeRepository.save(route);

        // Add flight taxes for the contribution
        List<FlightTax> flightTaxes = flightTaxService.saveFlightTaxes(contribution, route);

        return IContributionMapper.INSTANCE.toDto(contribution);
    }

    @Override
    @Transactional(readOnly = false)
    public ContributionDTO updateContribution(Long id, ContributionDTO contributionDTO) {
        Contribution contribution = contributionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found with id: " + id));
        IContributionMapper.INSTANCE.updateFromDto(contributionDTO, contribution);
        contribution = contributionRepository.save(contribution);

        return IContributionMapper.INSTANCE.toDto(contribution);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteContribution(Long id) {
        Contribution contribution = contributionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found with id: " + id));

        contribution.setRemovedAt(LocalDate.now());
        contributionRepository.save(contribution);
    }

    private void setHourBlockValues(final Route route, final Contribution contribution) {
        List<DistanceSpeedUtils> dsDataList = new ArrayList<>();

        // ToDo: Cotización sobre Rotación o Ruta??
        final Map<String, Airport> airportMap = route.getRotations().stream()
                .map(Route::getAirports)
                .flatMap(Set::stream)
                .map(RouteAirport::getAirport)
                .distinct()
                .collect(Collectors.toMap(Airport::getIataCode, airport -> airport));
        Double totalDistance = route.getRotations().stream()
                .map(Route::getFlights)
                .flatMap(List::stream)
                .map(flight -> getDistanceSpeedOfFlight(flight, contribution, airportMap, dsDataList))
                .mapToDouble(DistanceSpeedUtils::getDistance)
                .sum();
        Double totalTimeInHours = route.getRotations().stream()
                .map(Route::getFlights)
                .flatMap(List::stream)
                .map(flight -> getDistanceSpeedOfFlight(flight, contribution, airportMap, dsDataList))
                .mapToDouble(DistanceSpeedUtils::getTimeInHours)
                .sum();

        // Set values
        contribution.setBlockHour(contribution.getPurchasePrice().multiply(BigDecimal.valueOf(totalTimeInHours * 60D / 60D)));
//        contribution.setPricePerPax(contribution.getBlockHour().divide(contribution.get));
        int totalSeats = (contribution.getAircraft().getSeatingF() != null ? contribution.getAircraft().getSeatingF() : 0) +
                (contribution.getAircraft().getSeatingC() != null ? contribution.getAircraft().getSeatingC() : 0) +
                (contribution.getAircraft().getSeatingY() != null ? contribution.getAircraft().getSeatingY() : 0);
        contribution.setPricePerSeat(contribution.getBlockHour().divide(BigDecimal.valueOf(totalSeats), RoundingMode.HALF_EVEN));

    }

    private DistanceSpeedUtils getDistanceSpeedOfFlight(
            final @NotNull Flight flight, final @NotNull Contribution contribution,
            final @NotNull Map<String, Airport> airportMap, final @NotNull List<DistanceSpeedUtils> dsDataList) {
        Airport origin = airportMap.get(flight.getOrigin());
        Airport destination = airportMap.get(flight.getDestination());
        AircraftType aircraftType = contribution.getAircraft().getAircraftType();
        DistanceSpeedUtils dsData;
        Optional<DistanceSpeedUtils> optionalData = dsDataList.stream().filter(dsu ->
                dsu.getOriginId().equals(origin.getId()) &&
                        dsu.getDestinationId().equals(destination.getId()) &&
                        dsu.getAircraftTypeId().equals(aircraftType.getId()))
                .findFirst();
        if (optionalData.isPresent()) {
            dsData = optionalData.get();
        } else {
            dsData = Utils.calculateDistanceAndSpeed(conversionService, aircraftType, origin, destination);
            dsDataList.add(dsData);
        }

        return dsData;
    }
}
