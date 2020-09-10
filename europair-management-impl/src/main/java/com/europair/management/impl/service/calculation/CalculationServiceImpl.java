package com.europair.management.impl.service.calculation;

import com.europair.management.api.enums.FileServiceEnum;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.countries.entity.Country;
import com.europair.management.rest.model.files.entity.Client;
import com.europair.management.rest.model.files.entity.Provider;

import java.math.BigDecimal;

public class CalculationServiceImpl implements ICalculationService {

    private final String SPAIN_CODE = "ES";

    private final Double TAX_21 = 21D;
    private final Double TAX_10 = 10D;
    private final Double TAX_0 = 0D;
    private final Double TAX_FREE = null;


    // ToDo: un-mock when merged
    // @Autowired
    private ContributionRepository contributionRepository = new ContributionRepository();

    @Override
    public BigDecimal calculateIva(Long contributionId) {
//        Contribution contribution = contributionRepository.findById(contributionId);
        FileServiceEnum serviceType = FileServiceEnum.FLIGHT; // FIXME: de donde sacamos este valor?


        return null;
    }


    // Tax on sale (IVA devengado)

    // ToDo: aplicar condiciones de Baleares
    private Double getTaxOnSaleFlight(Airport origin, Airport destination) {
        return genericRouteSaleTaxCalculation(origin, destination, TAX_10);
    }

    // ToDo: aplicar condiciones de Baleares
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

    // ToDo: aplicar condiciones de Baleares
    private Double getTaxOnSaleAirportFee(Airport origin, Airport destination) {
        return genericRouteSaleTaxCalculation(origin, destination, TAX_10);
    }

    // ToDo: aplicar condiciones de Baleares
    private Double getTaxOnSaleExtrasOnBoard(Airport origin, Airport destination) {
        return genericRouteSaleTaxCalculation(origin, destination, TAX_10);
    }

    private Double getTaxOnSaleExtrasOnGround(Client client) {
        return genericClientSaleTaxCalculation(client, TAX_21);
    }

    // ToDo: aplicar condiciones de Baleares
    private Double getTaxOnSaleCateringOnBoard(Airport origin, Airport destination) {
        return genericRouteSaleTaxCalculation(origin, destination, TAX_10);
    }

    private Double getTaxOnSaleCateringOnGround(Client client) {
        // ToDo: 21% para bebidas alcoholicas, pendiente servicio aposta
        return genericClientSaleTaxCalculation(client, TAX_10);
    }

    // ToDo: aplicar condiciones de Baleares
    private Double getTaxOnSaleCancellationFee(Airport origin, Airport destination) {
        return genericRouteSaleTaxCalculation(origin, destination, TAX_10);
    }


    // Tax on purchase (IVA soportado)

    // ToDo: aplicar condiciones de Baleares
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

    // ToDo: aplicar condiciones de Baleares
    private Double getTaxOnPurchaseAirportFee(Airport origin, Airport destination, Provider provider) {
        return genericRouteAndProviderTaxCalculation(origin, destination, provider, TAX_10);
    }

    // ToDo: aplicar condiciones de Baleares
    private Double getTaxOnPurchaseExtrasOnBoard(Airport origin, Airport destination, Provider provider) {
        return genericRouteAndProviderTaxCalculation(origin, destination, provider, TAX_10);
    }

    private Double getTaxOnPurchaseExtrasOnGround(Provider provider) {
        return genericProviderPurchaseTaxCalculation(provider, TAX_21);
    }

    // ToDo: aplicar condiciones de Baleares
    private Double getTaxOnPurchaseCateringOnBoard(Airport origin, Airport destination, Provider provider) {
        // ToDo: 21% IVA para bebidas alcoholicas?
        return genericRouteAndProviderTaxCalculation(origin, destination, provider, TAX_10);
    }

    private Double getTaxOnPurchaseCateringOnGround(Provider provider) {
        // ToDo: 21% para bebidas alcoholicas, pendiente servicio aposta
        return genericProviderPurchaseTaxCalculation(provider, TAX_10);
    }

    // ToDo: aplicar condiciones de Baleares
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

    // Mock temporal classes

    static class ContributionRepository {
//        Contribution findById(Long id) {
//            Contribution c = new Contribution();
//
//
//            return c;
//        }
    }

    private Double getTaxBalearicIslands() {
        return Math.random(); // ToDo implementar recuperar las tasas de baleares según la ruta
    }


}
