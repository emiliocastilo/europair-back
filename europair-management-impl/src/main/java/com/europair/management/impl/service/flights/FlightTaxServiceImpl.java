package com.europair.management.impl.service.flights;

import com.europair.management.api.enums.FileServiceEnum;
import com.europair.management.impl.service.calculation.ICalculationService;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.flights.entity.Flight;
import com.europair.management.rest.model.flights.entity.FlightTax;
import com.europair.management.rest.model.flights.repository.FlightTaxRepository;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.entity.RouteAirport;
import com.europair.management.rest.model.servicetypes.entity.ServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        final Map<String, Airport> airportMap = route.getAirports().stream()
                .map(RouteAirport::getAirport)
                .distinct()
                .collect(Collectors.toMap(Airport::getIataCode, airport -> airport));

        if (!CollectionUtils.isEmpty(route.getFlights())) {
            FileServiceEnum serviceType = FileServiceEnum.FLIGHT; // FIXME de donde sacamos el tipo de servicio?
            boolean isSale = true; // FIXME de donde sacamos si es una venta o compra??

            for (Flight flight : route.getFlights()) {
                FlightTax ft = new FlightTax();
                ft.setContributionId(contribution.getId());
                ft.setFlightId(flight.getId());

                // Calculate flight taxes
                Airport origin = airportMap.get(flight.getOrigin());
                Airport destination = airportMap.get(flight.getDestination());

                Double tax = calculationService.calculateFlightTaxToApply(contribution, origin, destination, serviceType, isSale);
                ft.setTax(tax);
                // ToDo: precio * tax -> como calculamos el precio del vuelo ??
                ft.setTaxAmount(null);

                flightTaxes.add(ft);
            }
            flightTaxes = flightTaxRepository.saveAll(flightTaxes);
        }



        return flightTaxes;
    }
}
