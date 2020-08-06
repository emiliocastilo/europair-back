package com.europair.management.rest.conversions.service;

import com.europair.management.rest.model.conversions.dto.ConversionDataDTO;

import java.util.List;

public interface ConversionService {

    List<Double> convertData(ConversionDataDTO conversionDataDTO);

}