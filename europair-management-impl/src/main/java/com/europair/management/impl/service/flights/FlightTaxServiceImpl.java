package com.europair.management.impl.service.flights;

import com.europair.management.api.enums.OperationTypeEnum;
import com.europair.management.api.enums.ServiceTypeEnum;
import com.europair.management.impl.service.calculation.ICalculationService;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.flights.entity.Flight;
import com.europair.management.rest.model.flights.entity.FlightTax;
import com.europair.management.rest.model.flights.repository.FlightTaxRepository;
import com.europair.management.rest.model.routes.entity.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FlightTaxServiceImpl implements IFlightTaxService {

    @Autowired
    private FlightTaxRepository flightTaxRepository;

    @Autowired
    private ICalculationService calculationService;

    @Override
    public List<FlightTax> saveFlightTaxes(Contribution contribution, Route route) {
        List<FlightTax> flightTaxes = new ArrayList<>();
        List<Flight> routeFlights;

        if (route.getParentRoute() == null) {
            // Route
            routeFlights = route.getRotations().stream()
                    .map(Route::getFlights)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        } else {
            // Rotation
            routeFlights = route.getFlights();
        }

        if (CollectionUtils.isEmpty(routeFlights)) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Something went wrong. There are no flights in the contribution for the tax calculation.");
        }

        ServiceTypeEnum serviceType = ServiceTypeEnum.FLIGHT;
        if (contribution.getFile() != null && OperationTypeEnum.CHARGE.equals(contribution.getFile().getOperationType())) {
            serviceType = ServiceTypeEnum.CARGO;
        }

        for (Flight flight : routeFlights) {
            FlightTax ft = new FlightTax();
            ft.setContributionId(contribution.getId());
            ft.setFlightId(flight.getId());

            if (Boolean.TRUE.equals(flight.getPositionalFlight())) {
                // No taxes
                ft.setTaxOnSale(null);
                ft.setTaxOnPurchase(null);

            } else {
                // Calculate flight taxes
                Double taxOnSale = calculationService.calculateFinalTaxToApply(contribution.getFileId(), flight.getOrigin(),
                        flight.getDestination(), serviceType, true);
                ft.setTaxOnSale(taxOnSale);
                Double taxOnPurchase = calculationService.calculateFinalTaxToApply(contribution.getFileId(), flight.getOrigin(),
                        flight.getDestination(), serviceType, false);
                ft.setTaxOnPurchase(taxOnPurchase);
            }

            flightTaxes.add(ft);
        }
        flightTaxes = flightTaxRepository.saveAll(flightTaxes);

        return flightTaxes;
    }
}
