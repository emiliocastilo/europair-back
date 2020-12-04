package com.europair.management.impl.service.calculation;

import com.europair.management.api.enums.ServiceTypeEnum;
import com.europair.management.rest.model.airport.entity.Airport;
import org.apache.commons.lang3.tuple.Pair;

public interface ICalculationService {

    Pair<Double, Double> calculateTaxToApplyAndPercentage(Long fileId, Airport origin, Airport destination, ServiceTypeEnum serviceType, boolean isSale);

    Double calculateServiceTaxToApply(Long fileId, Airport origin, Airport destination, ServiceTypeEnum serviceType, boolean isSale);

    Double calculatePercentageOfTaxToApply(Airport origin, Airport destination, ServiceTypeEnum serviceType, boolean isSale);

    Pair<Double, Double> calculateTaxToApplyAndPercentage(Long fileId, Long originAirportId, Long destinationAirportId, ServiceTypeEnum serviceType, boolean isSale);

    Double calculateServiceTaxToApply(Long fileId, Long originAirportId, Long destinationAirportId, ServiceTypeEnum serviceType, boolean isSale);

    Double calculatePercentageOfTaxToApply(Long originAirportId, Long destinationAirportId, ServiceTypeEnum serviceType, boolean isSale);

}
