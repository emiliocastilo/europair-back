package com.europair.management.impl.service.calculation;

import com.europair.management.api.enums.FileServiceEnum;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.calculations.Contribution;
import com.europair.management.rest.model.files.entity.Client;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CalculationServiceImpl implements ICalculationService {

    private final String SPAIN_CODE = "ES";

    private final Double TAX_21 = 21D;
    private final Double TAX_10 = 10D;
    private final Double TAX_FREE = 0D;


    // ToDo: un-mock when merged
    // @Autowired
    private ContributionRepository contributionRepository = new ContributionRepository();

    @Override
    public BigDecimal calculateIva(Long contributionId) {
        Contribution contribution = contributionRepository.findById(contributionId);
        FileServiceEnum serviceType = FileServiceEnum.FLIGHT; // FIXME: de donde sacamos este valor?


        return null;
    }


    // IVA DEVENGADO

    private Double getFlightTax(Airport origin, Airport destination) {
        return genericRouteTaxCalculation(origin, destination, TAX_10);
    }

    private Double getCargoTax(Airport origin, Airport destination, Client client) {
        Double tax = null;
        switch (client.getType()) {
            case INDIVIDUAL -> tax = genericRouteTaxCalculation(origin, destination, TAX_21);
            case BUSINESS -> {
                if (isCanaryIslandsClient(client)) {
                    tax = TAX_FREE;
                } else if (isVIESClient(client)) {
                    tax = TAX_FREE;
                } else if (isNationalClient(client)) {
                    tax = TAX_21;
                } else {
                    tax = TAX_FREE;
                }
            }
        }

        return tax;
    }

    private Double getCommissionTax(Client client) {
    }

    private Double getTransportTax(Airport origin, Airport destination) {
        Double tax = TAX_FREE;
        if (isSpainInternalRoute(origin, destination) && !isCanaryIslandsRoute(origin, destination)) {
            tax = TAX_10;
        }

        return tax;
    }

    private Double getAirportTax(Airport origin, Airport destination) {
        return genericRouteTaxCalculation(origin, destination, TAX_10);
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

    private boolean isCanaryIslandsClient(Client client) {
        return Boolean.TRUE.equals(client.getCanaryIslands());
    }

    private boolean isVIESClient(Client client) {
        return Boolean.TRUE.equals(client.getVies());
    }


    private Double genericRouteTaxCalculation(final Airport origin, final Airport destination, final Double nationalTaxToApply) {
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
    
    private Double genericClientTaxCalculation(final Client client) {
        Double tax;
        if (isCanaryIslandsClient(client)) {
            tax = TAX_FREE;
        } else if (isVIESClient(client)) {
            tax = TAX_FREE;
        } else if (isNationalClient(client)) {
            tax = TAX_21;
        } else {
            tax = TAX_FREE;
        }
        return tax;
    }


    // Mock temporal classes

    static class ContributionRepository {
        Contribution findById(Long id) {
            Contribution c = new Contribution();


            return c;
        }
    }

    private Double getTaxBalearicIslands() {
        return Math.random(); // ToDo implementar recuperar las tasas de baleares según la ruta
    }


}
