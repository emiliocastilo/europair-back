package com.europair.management.impl.service.calculation;

import com.europair.management.api.enums.FileServiceEnum;
import com.europair.management.impl.common.exception.InvalidArgumentException;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.countries.entity.Country;
import com.europair.management.rest.model.files.entity.Client;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.files.entity.Provider;
import com.europair.management.rest.model.files.repository.FileRepository;
import com.europair.management.rest.model.routes.entity.RouteAirport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;

public class CalculationServiceImpl implements ICalculationService {

    private final String SPAIN_CODE = "ES";

    private final Double TAX_21 = 21D;
    private final Double TAX_10 = 10D;
    private final Double TAX_0 = 0D;
    private final Double TAX_FREE = null;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public Double calculateFlightTaxToApply(Contribution contribution, Airport origin, Airport destination, FileServiceEnum serviceType, boolean isSale) {
        File file = fileRepository.findById(contribution.getFileId())
                .orElseThrow(() -> new ResourceNotFoundException("File not found with id: " + contribution.getFileId()));

        Double taxToApply;
        if (isSale) {
            Client client = file.getClient();
            taxToApply = switch (serviceType) {
                case AIRPORT_TAX -> getTaxOnSaleAirportFee(origin, destination);
                case CANCEL_FEE -> getTaxOnSaleCancellationFee(origin, destination);
                case CARGO -> getTaxOnSaleCargo(origin, destination, client);
                case CATERING_ON_BOARD -> getTaxOnSaleCateringOnBoard(origin, destination);
                case CATERING_ON_GROUND -> getTaxOnSaleCateringOnGround(client);
                case COMMISSION -> getTaxOnSaleCommission(client);
                case EXTRAS_ON_BOARD -> getTaxOnSaleExtrasOnBoard(origin, destination);
                case EXTRAS_ON_GROUND -> getTaxOnSaleExtrasOnGround(client);
                case FLIGHT -> getTaxOnSaleFlight(origin, destination);
                case TRANSPORT -> getTaxOnSaleTransport(origin, destination);
            };
        } else {
            Provider provider = file.getProvider();
            taxToApply = switch (serviceType) {
                case AIRPORT_TAX -> getTaxOnPurchaseAirportFee(origin, destination, provider);
                case CANCEL_FEE -> getTaxOnPurchaseCancellationFee(origin, destination, provider);
                case CARGO -> getTaxOnPurchaseCargo(provider);
                case CATERING_ON_BOARD -> getTaxOnPurchaseCateringOnBoard(origin, destination, provider);
                case CATERING_ON_GROUND -> getTaxOnPurchaseCateringOnGround(provider);
                case COMMISSION -> getTaxOnPurchaseCommission(provider);
                case EXTRAS_ON_BOARD -> getTaxOnPurchaseExtrasOnBoard(origin, destination, provider);
                case EXTRAS_ON_GROUND -> getTaxOnPurchaseExtrasOnGround(provider);
                case FLIGHT -> getTaxOnPurchaseFlight(origin, destination, provider);
                case TRANSPORT -> getTaxOnPurchaseTransport(origin, destination, provider);
            };
        }
        Double taxPercentage = calculateTaxPercentageOnRoute(serviceType, isSale);

        return taxToApply != null ? taxToApply * (taxPercentage / 100D) : null;
    }

    @Override
    public Double calculateTaxToApply(Contribution contribution, FileServiceEnum serviceType, boolean isSale) {
        Airport origin = contribution.getRoute().getAirports().stream()
                .min(Comparator.comparing(RouteAirport::getOrder))
                .orElseThrow(() -> new InvalidArgumentException("No first airport found in the route with id: " + contribution.getRouteId()))
                .getAirport();
        Airport destination = contribution.getRoute().getAirports().stream()
                .max(Comparator.comparing(RouteAirport::getOrder))
                .orElseThrow(() -> new InvalidArgumentException("No last airport found in the route with id: " + contribution.getRouteId()))
                .getAirport();
        File file = fileRepository.findById(contribution.getFileId())
                .orElseThrow(() -> new ResourceNotFoundException("File not found with id: " + contribution.getFileId()));

        Double taxToApply;
        if (isSale) {
            Client client = file.getClient();
            taxToApply = switch (serviceType) {
                case AIRPORT_TAX -> getTaxOnSaleAirportFee(origin, destination);
                case CANCEL_FEE -> getTaxOnSaleCancellationFee(origin, destination);
                case CARGO -> getTaxOnSaleCargo(origin, destination, client);
                case CATERING_ON_BOARD -> getTaxOnSaleCateringOnBoard(origin, destination);
                case CATERING_ON_GROUND -> getTaxOnSaleCateringOnGround(client);
                case COMMISSION -> getTaxOnSaleCommission(client);
                case EXTRAS_ON_BOARD -> getTaxOnSaleExtrasOnBoard(origin, destination);
                case EXTRAS_ON_GROUND -> getTaxOnSaleExtrasOnGround(client);
                case FLIGHT -> getTaxOnSaleFlight(origin, destination);
                case TRANSPORT -> getTaxOnSaleTransport(origin, destination);
            };
        } else {
            Provider provider = file.getProvider();
            taxToApply = switch (serviceType) {
                case AIRPORT_TAX -> getTaxOnPurchaseAirportFee(origin, destination, provider);
                case CANCEL_FEE -> getTaxOnPurchaseCancellationFee(origin, destination, provider);
                case CARGO -> getTaxOnPurchaseCargo(provider);
                case CATERING_ON_BOARD -> getTaxOnPurchaseCateringOnBoard(origin, destination, provider);
                case CATERING_ON_GROUND -> getTaxOnPurchaseCateringOnGround(provider);
                case COMMISSION -> getTaxOnPurchaseCommission(provider);
                case EXTRAS_ON_BOARD -> getTaxOnPurchaseExtrasOnBoard(origin, destination, provider);
                case EXTRAS_ON_GROUND -> getTaxOnPurchaseExtrasOnGround(provider);
                case FLIGHT -> getTaxOnPurchaseFlight(origin, destination, provider);
                case TRANSPORT -> getTaxOnPurchaseTransport(origin, destination, provider);
            };
        }

        return taxToApply;
    }

    @Override
    public Double calculateTaxPercentageOnRoute(FileServiceEnum serviceType, boolean isSale) {
        Double taxPercentage = 100D;
        if (checkBalearicIslandsSpecialConditions(isSale, serviceType)) {
            taxPercentage = getTaxBalearicIslands();
        }

        return taxPercentage;
    }

    // Tax on sale (IVA devengado)

    private Double getTaxOnSaleFlight(Airport origin, Airport destination) {
        return genericRouteSaleTaxCalculation(origin, destination, TAX_10);
    }

    private Double getTaxOnSaleCargo(Airport origin, Airport destination, Client client) {
        Double tax = null;
        switch (client.getType()) {
            case INDIVIDUAL -> tax = genericRouteSaleTaxCalculation(origin, destination, TAX_21);
            case BUSINESS -> tax = genericClientSaleTaxCalculation(client, TAX_21);
        }
        return tax;
    }

    private Double getTaxOnSaleCommission(Client client) {
        return genericClientSaleTaxCalculation(client, TAX_21);
    }

    private Double getTaxOnSaleTransport(Airport origin, Airport destination) {
        Double tax = TAX_FREE;
        if (isSpainInternalRoute(origin, destination) && !isCanaryIslandsRoute(origin, destination)) {
            tax = TAX_10;
        }

        return tax;
    }

    private Double getTaxOnSaleAirportFee(Airport origin, Airport destination) {
        return genericRouteSaleTaxCalculation(origin, destination, TAX_10);
    }

    private Double getTaxOnSaleExtrasOnBoard(Airport origin, Airport destination) {
        return genericRouteSaleTaxCalculation(origin, destination, TAX_10);
    }

    private Double getTaxOnSaleExtrasOnGround(Client client) {
        return genericClientSaleTaxCalculation(client, TAX_21);
    }

    private Double getTaxOnSaleCateringOnBoard(Airport origin, Airport destination) {
        return genericRouteSaleTaxCalculation(origin, destination, TAX_10);
    }

    private Double getTaxOnSaleCateringOnGround(Client client) {
        // ToDo: 21% para bebidas alcoholicas, pendiente servicio aposta
        return genericClientSaleTaxCalculation(client, TAX_10);
    }

    private Double getTaxOnSaleCancellationFee(Airport origin, Airport destination) {
        return genericRouteSaleTaxCalculation(origin, destination, TAX_10);
    }


    // Tax on purchase (IVA soportado)

    private Double getTaxOnPurchaseFlight(Airport origin, Airport destination, Provider provider) {
        return genericRouteAndProviderTaxCalculation(origin, destination, provider, TAX_10);
    }

    private Double getTaxOnPurchaseCargo(Provider provider) {
        return genericProviderPurchaseTaxCalculation(provider, TAX_21);
    }

    private Double getTaxOnPurchaseCommission(Provider provider) {
        return genericProviderPurchaseTaxCalculation(provider, TAX_21);
    }

    private Double getTaxOnPurchaseTransport(Airport origin, Airport destination, Provider provider) {
        Double tax = null;
        if (isSpainInternalRoute(origin, destination)) {
            if (isCanaryIslandsInternalRoute(origin, destination)) {
                if (isCanaryIslandsProvider(provider)) {
                    // ToDo: aplicar IGIC
                } else {
                    tax = TAX_FREE;
                }
//            } else if (isCanaryIslandsRoute(origin, destination)) {
//                tax = TAX_FREE;
            } else if (isSpainProvider(provider)) {
                tax = TAX_10;
            } else {
                tax = TAX_FREE;
            }
        } else if (isDomesticRoute(origin, destination)) {
            if (isProviderNationalityOfRoute(provider, destination)) {
                taxFromOtherCountry(provider.getCountry());
            } else {
                tax = TAX_FREE;
            }
        } else {
            tax = TAX_FREE;
        }
        return tax;
    }

    private Double getTaxOnPurchaseAirportFee(Airport origin, Airport destination, Provider provider) {
        return genericRouteAndProviderTaxCalculation(origin, destination, provider, TAX_10);
    }

    private Double getTaxOnPurchaseExtrasOnBoard(Airport origin, Airport destination, Provider provider) {
        return genericRouteAndProviderTaxCalculation(origin, destination, provider, TAX_10);
    }

    private Double getTaxOnPurchaseExtrasOnGround(Provider provider) {
        return genericProviderPurchaseTaxCalculation(provider, TAX_21);
    }

    private Double getTaxOnPurchaseCateringOnBoard(Airport origin, Airport destination, Provider provider) {
        // ToDo: 21% IVA para bebidas alcoholicas?
        return genericRouteAndProviderTaxCalculation(origin, destination, provider, TAX_10);
    }

    private Double getTaxOnPurchaseCateringOnGround(Provider provider) {
        // ToDo: 21% para bebidas alcoholicas, pendiente servicio aposta
        return genericProviderPurchaseTaxCalculation(provider, TAX_10);
    }

    private Double getTaxOnPurchaseCancellationFee(Airport origin, Airport destination, Provider provider) {
        return genericRouteAndProviderTaxCalculation(origin, destination, provider, TAX_21);
    }


    // Utils

    private boolean isCanaryIslandsRoute(Airport origin, Airport destination) {
        return Boolean.TRUE.equals(origin.getCanaryIslands() || Boolean.TRUE.equals(destination.getCanaryIslands()));
    }

    private boolean isBalearicIslandsRoute(Airport origin, Airport destination) {
        return Boolean.TRUE.equals(origin.getBalearics() || Boolean.TRUE.equals(destination.getBalearics()));
    }

    private boolean isSpainInternalRoute(Airport origin, Airport destination) {
        return SPAIN_CODE.equals(origin.getCountry().getCode()) && SPAIN_CODE.equals(destination.getCountry().getCode());
    }

    private boolean isNationalClient(Client client) {
        return SPAIN_CODE.equals(client.getCountry().getCode());
    }

    private boolean isSpainProvider(Provider provider) {
        return SPAIN_CODE.equals(provider.getCountry().getCode());
    }

    private boolean isInternationalProvider(Provider provider) {
        return !Boolean.TRUE.equals(provider.getCountry().getEuropeanUnion());
    }

    private boolean isCanaryIslandsClient(Client client) {
        return Boolean.TRUE.equals(client.getCanaryIslands());
    }

    private boolean isCanaryIslandsProvider(Provider provider) {
        return Boolean.TRUE.equals(provider.getCanaryIslands());
    }

    private boolean isVIESClient(Client client) {
        return Boolean.TRUE.equals(client.getVies());
    }

    private boolean isVIESProvider(Provider provider) {
        return Boolean.TRUE.equals(provider.getVies());
    }

    private boolean isProviderNationalityOfRoute(Provider provider, Airport destination) {
        String routeNationality = destination.getCountry().getCode();
        return routeNationality.equals(provider.getCountry().getCode());
    }

    private boolean isInternationalRoute(Airport origin, Airport destination) {
        return !(Boolean.TRUE.equals(origin.getCountry().getEuropeanUnion()) && Boolean.TRUE.equals(destination.getCountry().getEuropeanUnion()));
    }

    private boolean isEUInternalRoute(Airport origin, Airport destination) {
        return Boolean.TRUE.equals(origin.getCountry().getEuropeanUnion()) && Boolean.TRUE.equals(destination.getCountry().getEuropeanUnion());
    }

    private boolean isCanaryIslandsInternalRoute(Airport origin, Airport destination) {
        return Boolean.TRUE.equals(origin.getCanaryIslands()) && Boolean.TRUE.equals(destination.getCanaryIslands());
    }

    private boolean isDomesticRoute(Airport origin, Airport destination) {
        return origin.getCountry().getId().equals(destination.getCountry().getId());
    }


    private Double taxPercentageToApply(Airport origin, Airport destination) {
        Double tax = 100D;
        if (isBalearicIslandsRoute(origin, destination)) {
            tax = getTaxBalearicIslands();
        }
        return tax;
    }


    private Double genericRouteSaleTaxCalculation(final Airport origin, final Airport destination, final Double nationalTaxToApply) {
        Double tax;
        if (isCanaryIslandsRoute(origin, destination)) {
            tax = TAX_FREE;
        } else if (isSpainInternalRoute(origin, destination)) {
            tax = nationalTaxToApply;
        } else {
            tax = TAX_FREE;
        }
        return tax;
    }

    private Double genericClientSaleTaxCalculation(final Client client, final Double nationalTaxToApply) {
        Double tax;
        if (isCanaryIslandsClient(client)) {
            tax = TAX_FREE;
        } else if (isVIESClient(client)) {
            tax = TAX_0;
        } else if (isNationalClient(client)) {
            tax = nationalTaxToApply;
        } else {
            tax = TAX_FREE;
        }
        return tax;
    }

    private Double genericProviderPurchaseTaxCalculation(final Provider provider, final Double nationalTaxToApply) {
        Double tax = null;
        if (isInternationalProvider(provider) || isCanaryIslandsProvider(provider)) {
            // ToDo: revisar documento
        } else if (isSpainProvider(provider)) {
            tax = nationalTaxToApply;
        } else if (isVIESProvider(provider)) {
            tax = TAX_0;
        } else {
            taxFromOtherCountry(provider.getCountry());
        }
        return tax;
    }

    private Double genericRouteAndProviderTaxCalculation(final Airport origin, final Airport destination,
                                                         final Provider provider, final Double nationalTaxToApply) {
        Double tax = null;
        if (isSpainInternalRoute(origin, destination)) {
            if (isCanaryIslandsInternalRoute(origin, destination)) {
                if (isCanaryIslandsProvider(provider)) {
                    // ToDo: aplicar IGIC
                } else {
                    tax = TAX_FREE;
                }
            } else if (isCanaryIslandsRoute(origin, destination)) {
                tax = TAX_FREE;
            } else if (isSpainProvider(provider)) {
                tax = nationalTaxToApply;
            } else {
                tax = TAX_FREE;
            }
        } else if (isDomesticRoute(origin, destination)) {
            if (isProviderNationalityOfRoute(provider, destination)) {
                taxFromOtherCountry(provider.getCountry());
            } else {
                tax = TAX_FREE;
            }
        } else {
            tax = TAX_FREE;
        }
        return tax;
    }

    private void taxFromOtherCountry(Country country) {
        // ToDo: definir exception específica
        throw new ResourceNotFoundException("Must apply tax from: " + country.getCode() + " - " + country.getName());
    }

    private Double getTaxBalearicIslands() {
        return Math.random(); // FIXME: implementar recuperar las tasas de baleares según la ruta
    }

    private boolean checkBalearicIslandsSpecialConditions(boolean isSale, FileServiceEnum service) {
        boolean hasBalearicIslandSpecialConditions = false;

        switch (service) {
            case FLIGHT, AIRPORT_TAX, EXTRAS_ON_BOARD, CATERING_ON_BOARD, CANCEL_FEE -> hasBalearicIslandSpecialConditions = true;
            case CARGO -> hasBalearicIslandSpecialConditions = isSale;
        }

        return hasBalearicIslandSpecialConditions;
    }

}
