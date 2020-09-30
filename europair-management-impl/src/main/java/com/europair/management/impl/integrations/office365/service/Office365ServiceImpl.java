package com.europair.management.impl.integrations.office365.service;

import com.europair.management.api.integrations.office365.dto.ContributionDataDto;
import com.europair.management.api.integrations.office365.dto.FlightExtendedInfoDto;
import com.europair.management.api.integrations.office365.dto.FlightServiceDataDto;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.contributions.repository.ContributionRepository;
import com.europair.management.rest.model.fleet.entity.AircraftBase;
import com.europair.management.rest.model.flights.entity.FlightService;
import com.europair.management.rest.model.flights.repository.FlightServiceRepository;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class Office365ServiceImpl implements IOffice365Service {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    private FlightServiceRepository flightServiceRepository;

    @Override
    public void confirmOperation(Long routeId, Long contributionId) {

        // ToDo: get data
        Route route = routeRepository.findById(routeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId));

        Contribution contribution = contributionRepository.findById(routeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found with id: " + contributionId));


        // ToDo: convert


        // ToDo: send data


    }


    // Mapping functions

    private List<FlightExtendedInfoDto> mapFlightsWithServices(final Route route, final Contribution contribution) {
        List<FlightExtendedInfoDto> dtoList = new ArrayList<>();
        route.getFlights().forEach(flight -> {
            FlightExtendedInfoDto dto = new FlightExtendedInfoDto();
            dto.setOperationType(route.getFile().getOperationType());
            dto.setPaxTotalNumber(flight.getSeatsC() + flight.getSeatsF() + flight.getSeatsY());
            dto.setBedsNumber(flight.getBeds());
            dto.setOriginAirport(flight.getOrigin());
            dto.setDestinationAirport(flight.getDestination());
            dto.setStartDate(flight.getDepartureTime());
            dto.setEndDate(flight.getDepartureTime()); // ToDo: calcular tiempo de vuelo y sumarlo
            dto.setPlateNumber(contribution.getAircraft().getPlateNumber());
            dto.setOperator(contribution.getOperator().getName());
            dto.setStretchersNumber(flight.getStretchers());
            dto.setCharge(null); // ToDo: de donde lo sacamos?
            dto.setClient(null); // ToDo: de donde lo sacamos?
            dto.setFlightNumber(null);// ToDo: de donde lo sacamos?

            dto.setServices(mapFlightServices(flight.getId()));

            dtoList.add(dto);
        });

        return dtoList;
    }

    private List<FlightServiceDataDto> mapFlightServices(final Long flightId) {
        List<FlightServiceDataDto> dtoList;
        List<FlightService> flightServices = flightServiceRepository.findAllByFlightId(flightId);

        if (CollectionUtils.isEmpty(flightServices)) {
            dtoList = null;
        } else {
            dtoList = new ArrayList<>();
            flightServices.forEach(flightService -> {
                // ToDo: esto se puede pasar a un mapper [FLIGHT-SERVICE]
                FlightServiceDataDto dto = new FlightServiceDataDto();
                dto.setCode(flightService.getService().getCode());
                dto.setComments(flightService.getComments());
                dto.setDescription(flightService.getDescription());
                dto.setProvider(flightService.getProvider().getCode() + " - " + flightService.getProvider().getName());
                dto.setPurchaseAmount(flightService.getPurchasePrice());
                dto.setQuantity(flightService.getQuantity());
                dto.setSaleAmount(flightService.getSalePrice());
                dto.setStatus(flightService.getStatus());

                dtoList.add(dto);
            });
        }

        return dtoList;
    }

    private ContributionDataDto mapContribution(final Route route, final Contribution contribution) {
        ContributionDataDto dto = new ContributionDataDto();

        AircraftBase mainBase = contribution.getAircraft().getBases().stream()
                .filter(AircraftBase::getMainBase).findFirst().orElse(null);

        dto.setAircraftType(contribution.getAircraft().getAircraftType().getCode());
        dto.setAircraftBase(mainBase == null ? null : mainBase.getAirport().getName());
        dto.setAircraftTotalSpeed(null); // ToDo: como calculamos esto??

        dto.setCurrencyOnPurchase(contribution.getCurrency());
        dto.setExchangeTypeOnPurchase(contribution.getExchangeBuyType());

        dto.setPurchasePrice(contribution.getPurchasePrice());
        dto.setPurchaseCommissionPercentage(contribution.getPurchaseCommissionPercent().doubleValue());
        dto.setPurchaseCommissionAmount(dto.getPurchasePrice().multiply(
                BigDecimal.valueOf(dto.getPurchaseCommissionPercentage() / 100)));
        dto.setPurchaseNetPrice(dto.getPurchasePrice().add(dto.getPurchaseCommissionAmount()));

        dto.setIncludedVAT(contribution.getIncludedVAT());

        dto.setCurrencyOnSale(contribution.getCurrency()); // ToDo: de donde lo sacamos?
        dto.setExchangeTypeOnSale(contribution.getExchangeBuyType()); // ToDo: de donde lo sacamos?

        dto.setPurchasePriceOnSaleCurrency(contribution.getPurchasePrice()); // ToDo: calcular después de sacar el tipo de cambio de venta

        dto.setSalePrice(contribution.getSalesPrice());
        dto.setSaleCommissionPercentage(contribution.getSalesCommissionPercent().doubleValue());
        dto.setSaleCommissionAmount(dto.getSalePrice().multiply(
                BigDecimal.valueOf(dto.getSaleCommissionPercentage() / 100)));
        dto.setSaleNetPrice(dto.getSalePrice().add(dto.getSaleCommissionAmount()));

        dto.setMarginPercentage(dto.getSalePrice().multiply(BigDecimal.valueOf(100d))
                .divide(dto.getPurchasePrice()).doubleValue() - 100);
        dto.setMarginAmount(dto.getSalePrice().subtract(dto.getPurchasePrice()));

        dto.setSeller(null); // ToDo: de donde lo sacamos??

        return dto;
    }
}
