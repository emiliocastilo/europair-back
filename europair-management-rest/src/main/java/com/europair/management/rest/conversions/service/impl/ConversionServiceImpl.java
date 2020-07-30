package com.europair.management.rest.conversions.service.impl;

import com.europair.management.rest.conversions.repository.ConversionRepository;
import com.europair.management.rest.conversions.service.ConversionService;
import com.europair.management.rest.model.conversions.common.Unit;
import com.europair.management.rest.model.conversions.dto.ConversionDataDTO;
import com.europair.management.rest.model.conversions.entity.Conversion;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConversionServiceImpl implements ConversionService {

    private final ConversionRepository conversionRepository;

    @Getter(lazy = true)
    private final List<Conversion> flatConversionList = conversionRepository.findAll();

    @Getter(lazy = true)
    private final Map<Unit, Map<Unit, Double>> conversionTable = loadConversionTable();

    @Getter(lazy = true)
    private final Map<Unit, Map<Unit, Double>> reversedConversionTable = loadReversedConversionTable();

    @Override
    public List<Double> convertData(final ConversionDataDTO conversionDataDTO) {

        Unit srcUnit = conversionDataDTO.getSrcUnit();
        Unit dstUnit = conversionDataDTO.getDstUnit();

        if (srcUnit != null) {
            Map<Unit, Double> conversionFactors = getConversionTable().get(srcUnit);
            return conversionDataDTO.getDataToConvert().stream()
                    // input validation
                    .filter(data -> data.getSrcUnit() == null && data.getDstUnit() != null)
                    // conversion
                    .map(data -> srcUnit.equals(data.getDstUnit()) ?
                            data.getValue() : conversionFactors.get(data.getDstUnit()) * data.getValue())
                    .collect(Collectors.toList());
        }

        if (dstUnit != null) {
            Map<Unit, Double> conversionFactors = getReversedConversionTable().get(dstUnit);
            return conversionDataDTO.getDataToConvert().stream()
                    // input validation
                    .filter(data -> data.getDstUnit() == null && data.getSrcUnit() != null)
                    // conversion
                    .map(data -> dstUnit.equals(data.getSrcUnit()) ?
                            data.getValue() : conversionFactors.get(data.getSrcUnit()) * data.getValue())
                    .collect(Collectors.toList());
        }

        return null;
    }

    private Map<Unit, Map<Unit, Double>> loadConversionTable() {

        return getFlatConversionList().stream()
                .collect(Collectors.groupingBy(Conversion::getSrcUnit,
                        Collectors.toMap(Conversion::getDstUnit, conversion -> conversion.getFactor())));
    }

    private Map<Unit, Map<Unit, Double>> loadReversedConversionTable() {

        return getFlatConversionList().stream()
                .collect(Collectors.groupingBy(Conversion::getDstUnit,
                        Collectors.toMap(Conversion::getSrcUnit, conversion -> conversion.getFactor())));
    }
}
