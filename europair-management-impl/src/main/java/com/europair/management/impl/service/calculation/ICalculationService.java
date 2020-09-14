package com.europair.management.impl.service.calculation;

import com.europair.management.api.enums.FileServiceEnum;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.contributions.entity.Contribution;

public interface ICalculationService {

    Double calculateFlightTaxToApply(Contribution contribution, Airport origin, Airport destination, FileServiceEnum serviceType, boolean isSale);

    Double calculateTaxToApply(Contribution contribution, FileServiceEnum serviceType, boolean isSale);

    Double calculateTaxPercentageOnRoute(FileServiceEnum serviceType, boolean isSale);

}
