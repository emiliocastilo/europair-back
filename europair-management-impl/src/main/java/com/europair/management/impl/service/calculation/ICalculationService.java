package com.europair.management.impl.service.calculation;

import java.math.BigDecimal;

public interface ICalculationService {

    BigDecimal calculateIva(Long contributionId);

}
