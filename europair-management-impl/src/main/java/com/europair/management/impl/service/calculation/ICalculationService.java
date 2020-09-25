package com.europair.management.impl.service.calculation;

import com.europair.management.api.enums.ServiceTypeEnum;
import com.europair.management.rest.model.airport.entity.Airport;

public interface ICalculationService {

    Double calculateFinalTaxToApply(Long fileId, Airport origin, Airport destination, ServiceTypeEnum serviceType, boolean isSale);

    Double calculateServiceTaxToApply(Long fileId, Airport origin, Airport destination, ServiceTypeEnum serviceType, boolean isSale);

    Double calculateTaxPercentageOnRoute(Airport origin, Airport destination, ServiceTypeEnum serviceType, boolean isSale);

}
