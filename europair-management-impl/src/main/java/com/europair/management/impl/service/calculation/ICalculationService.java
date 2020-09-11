package com.europair.management.impl.service.calculation;

import com.europair.management.rest.model.contributions.entity.Contribution;

public interface ICalculationService {

    Double calculateTaxToApply(Contribution contribution, boolean isSale);

    Double calculateTaxPercentageOnRoute(Contribution contribution, boolean isSale);

}
