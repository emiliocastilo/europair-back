package com.europair.management.impl.service.calculation;

import com.europair.management.api.dto.taxes.RouteBalearicsPctVatDTO;
import com.europair.management.api.enums.FileServiceEnum;
import com.europair.management.impl.service.taxes.IRouteBalearicsPctVatService;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.countries.entity.Country;
import com.europair.management.rest.model.files.entity.Client;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.files.entity.Provider;
import com.europair.management.rest.model.files.repository.FileRepository;
import com.europair.management.rest.model.taxes.repository.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CalculationServiceImpl implements ICalculationService {

    private final Double TAX_0 = 0D;
    private final Double TAX_FREE = null;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private TaxRepository taxRepository;

    @Autowired
    private IRouteBalearicsPctVatService balearicsPctVatService;

    @Override
    public Double calculateFinalTaxToApply(Long fileId, Airport origin, Airport destination, FileServiceEnum serviceType, boolean isSale) {
        Double taxToApply = calculateServiceTaxToApply(fileId, origin, destination, serviceType, isSale);
        Double taxPercentage = calculateTaxPercentageOnRoute(origin, destination, serviceType, isSale);

        return taxToApply != null ? taxToApply * (taxPercentage / 100D) : null;
    }

    @Override
    public Double calculateServiceTaxToApply(Long fileId, Airport origin, Airport destination, FileServiceEnum serviceType, boolean isSale) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found with id: " + fileId));

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
    public Double calculateTaxPercentageOnRoute(Airport origin, Airport destination, FileServiceEnum serviceType, boolean isSale) {
        Double taxPercentage = 100D;
        if (checkBalearicIslandsSpecialConditions(isSale, serviceType)) {
            taxPercentage = getTaxBalearicIslands(origin.getId(), destination.getId());
        }

        return taxPercentage;
    }

    // Tax on sale (IVA devengado)

    private Double getTaxOnSaleFlight(Airport origin, Airport destination) {
        return genericRouteSaleTaxCalculation(origin, destination, getSpainReducedTax());
    }

    private Double getTaxOnSaleCargo(Airport origin, Airport destination, Client client) {
        Double tax = null;
        switch (client.getType()) {
            case INDIVIDUAL -> tax = genericRouteSaleTaxCalculation(origin, destination, getSpainTax());
            case BUSINESS -> tax = genericClientSaleTaxCalculation(client, getSpainTax());
        }
        return tax;
    }

    private Double getTaxOnSaleCommission(Client client) {
        return genericClientSaleTaxCalculation(client, getSpainTax());
    }

    private Double getTaxOnSaleTransport(Airport origin, Airport destination) {
        Double tax = TAX_FREE;
        if (isSpainInternalRoute(origin, destination) && !isCanaryIslandsRoute(origin, destination)) {
            tax = getSpainReducedTax();
        }

        return tax;
    }

    private Double getTaxOnSaleAirportFee(Airport origin, Airport destination) {
        return genericRouteSaleTaxCalculation(origin, destination, getSpainReducedTax());
    }

    private Double getTaxOnSaleExtrasOnBoard(Airport origin, Airport destination) {
        return genericRouteSaleTaxCalculation(origin, destination, getSpainReducedTax());
    }

    private Double getTaxOnSaleExtrasOnGround(Client client) {
        return genericClientSaleTaxCalculation(client, getSpainTax());
    }

    private Double getTaxOnSaleCateringOnBoard(Airport origin, Airport destination) {
        return genericRouteSaleTaxCalculation(origin, destination, getSpainReducedTax());
    }

    private Double getTaxOnSaleCateringOnGround(Client client) {
        // ToDo: 21% para bebidas alcoholicas, pendiente servicio aposta
        return genericClientSaleTaxCalculation(client, getSpainReducedTax());
    }

    private Double getTaxOnSaleCancellationFee(Airport origin, Airport destination) {
        return genericRouteSaleTaxCalculation(origin, destination, getSpainReducedTax());
    }


    // Tax on purchase (IVA soportado)

    private Double getTaxOnPurchaseFlight(Airport origin, Airport destination, Provider provider) {
        return genericRouteAndProviderTaxCalculation(origin, destination, provider, getSpainReducedTax());
    }

    private Double getTaxOnPurchaseCargo(Provider provider) {
        return genericProviderPurchaseTaxCalculation(provider, getSpainTax());
    }

    private Double getTaxOnPurchaseCommission(Provider provider) {
        return genericProviderPurchaseTaxCalculation(provider, getSpainTax());
    }

    private Double getTaxOnPurchaseTransport(Airport origin, Airport destination, Provider provider) {
        Double tax = null;
        if (isSpainInternalRoute(origin, destination)) {
            if (isCanaryIslandsInternalRoute(origin, destination)) {
                if (isCanaryIslandsProvider(provider)) {
                    tax = getIGICTax();
                } else {
                    tax = TAX_FREE;
                }
//            } else if (isCanaryIslandsRoute(origin, destination)) {
//                tax = TAX_FREE;
            } else if (isSpainProvider(provider)) {
                tax = getSpainReducedTax();
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
        return genericRouteAndProviderTaxCalculation(origin, destination, provider, getSpainReducedTax());
    }

    private Double getTaxOnPurchaseExtrasOnBoard(Airport origin, Airport destination, Provider provider) {
        return genericRouteAndProviderTaxCalculation(origin, destination, provider, getSpainReducedTax());
    }

    private Double getTaxOnPurchaseExtrasOnGround(Provider provider) {
        return genericProviderPurchaseTaxCalculation(provider, getSpainTax());
    }

    private Double getTaxOnPurchaseCateringOnBoard(Airport origin, Airport destination, Provider provider) {
        // ToDo: 21% IVA para bebidas alcoholicas?
        return genericRouteAndProviderTaxCalculation(origin, destination, provider, getSpainReducedTax());
    }

    private Double getTaxOnPurchaseCateringOnGround(Provider provider) {
        // ToDo: 21% para bebidas alcoholicas, pendiente servicio aposta
        return genericProviderPurchaseTaxCalculation(provider, getSpainReducedTax());
    }

    private Double getTaxOnPurchaseCancellationFee(Airport origin, Airport destination, Provider provider) {
        return genericRouteAndProviderTaxCalculation(origin, destination, provider, getSpainTax());
    }


    // Utils

    private boolean isCanaryIslandsRoute(Airport origin, Airport destination) {
        return Boolean.TRUE.equals(origin.getCanaryIslands() || Boolean.TRUE.equals(destination.getCanaryIslands()));
    }

    private boolean isBalearicIslandsRoute(Airport origin, Airport destination) {
        return Boolean.TRUE.equals(origin.getBalearics() || Boolean.TRUE.equals(destination.getBalearics()));
    }

    private boolean isSpainInternalRoute(Airport origin, Airport destination) {
        return Utils.Constants.SPAIN_CODE.equals(origin.getCountry().getCode()) && Utils.Constants.SPAIN_CODE.equals(destination.getCountry().getCode());
    }

    private boolean isNationalClient(Client client) {
        return Utils.Constants.SPAIN_CODE.equals(client.getCountry().getCode());
    }

    private boolean isSpainProvider(Provider provider) {
        return Utils.Constants.SPAIN_CODE.equals(provider.getCountry().getCode());
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
                    tax = getIGICTax();
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

    private boolean checkBalearicIslandsSpecialConditions(boolean isSale, FileServiceEnum service) {
        boolean hasBalearicIslandSpecialConditions = false;

        switch (service) {
            case FLIGHT, AIRPORT_TAX, EXTRAS_ON_BOARD, CATERING_ON_BOARD, CANCEL_FEE -> hasBalearicIslandSpecialConditions = true;
            case CARGO -> hasBalearicIslandSpecialConditions = isSale;
        }

        return hasBalearicIslandSpecialConditions;
    }


    // Get Tax values

    private Double getTaxBalearicIslands(Long originId, Long destinationId) {
        RouteBalearicsPctVatDTO vatDTO =
                balearicsPctVatService.findByOriginAndDestinationWithInverseSearch(originId, destinationId);
        return vatDTO.getPercentage();
    }

    private void taxFromOtherCountry(Country country) {
        throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "The tax that applies if from another country, must apply tax manually from: " +
                country.getCode() + " - " + country.getName());
    }

    private Double getSpainTax() {
        return taxRepository.findFirstByCode(Utils.Constants.TAX_ES_CODE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Tax found for code: " + Utils.Constants.TAX_ES_CODE))
                .getTaxPercentage();
    }

    private Double getSpainReducedTax() {
        return taxRepository.findFirstByCode(Utils.Constants.TAX_ES_REDUCED_CODE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Tax found for code: " + Utils.Constants.TAX_ES_REDUCED_CODE))
                .getTaxPercentage();
    }

    private Double getIGICTax() {
        return taxRepository.findFirstByCode(Utils.Constants.TAX_ES_IGIC_CODE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Tax found for code: " + Utils.Constants.TAX_ES_IGIC_CODE))
                .getTaxPercentage();
    }

}
