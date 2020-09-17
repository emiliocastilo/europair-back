package com.europair.management.impl.service.calculation;

import com.europair.management.api.enums.FileServiceEnum;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.routes.entity.Route;

public interface ICalculationService {

    Double calculateFinalTaxToApply(Long fileId, Airport origin, Airport destination, FileServiceEnum serviceType, boolean isSale);

    Double calculateServiceTaxToApply(Long fileId, Airport origin, Airport destination, FileServiceEnum serviceType, boolean isSale);

    Double calculateTaxPercentageOnRoute(Airport origin, Airport destination, FileServiceEnum serviceType, boolean isSale);

}
