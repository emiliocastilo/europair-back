package com.europair.management.impl.service.calculation;

import com.europair.management.api.enums.ServiceTypeEnum;
import com.europair.management.rest.model.airport.entity.Airport;
import org.springframework.data.util.Pair;

public interface ICalculationService {

    Pair<Double, Double> calculateTaxToApplyAndPercentage(Long fileId, Airport origin, Airport destination, ServiceTypeEnum serviceType, boolean isSale);

    Double calculateServiceTaxToApply(Long fileId, Airport origin, Airport destination, ServiceTypeEnum serviceType, boolean isSale);

    Double calculatePercentageOfTaxToApply(Airport origin, Airport destination, ServiceTypeEnum serviceType, boolean isSale);

}
