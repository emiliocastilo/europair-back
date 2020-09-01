package com.europair.management.impl.service.conversions;

import com.europair.management.api.dto.conversions.ConversionDataDTO;

import java.util.List;

public interface ConversionService {

    List<Double> convertData(ConversionDataDTO conversionDataDTO);

}