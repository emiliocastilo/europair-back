package com.europair.management.impl.service.calculation;

import com.europair.management.api.dto.calculation.VatCalculationRequestDto;
import com.europair.management.api.dto.calculation.VatCalculationResponseDto;
import com.europair.management.api.service.calculation.ICalculationController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Slf4j
public class CalculationController implements ICalculationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculationController.class);

    @Autowired
    private ICalculationService calculationService;

    @Override
    public ResponseEntity<VatCalculationResponseDto> calculateVat(@NotNull @Valid VatCalculationRequestDto vatCalculationRequestDto) {
        Pair<Double, Double> result = calculationService.calculateTaxToApplyAndPercentage(
                vatCalculationRequestDto.getFileId(),
                vatCalculationRequestDto.getOriginAirportId(),
                vatCalculationRequestDto.getDestinationAirportId(),
                vatCalculationRequestDto.getServiceType(),
                vatCalculationRequestDto.isSale());
        return ResponseEntity.ok(new VatCalculationResponseDto(result.getLeft(), result.getRight()));
    }

    @Override
    public ResponseEntity<Double> calculateVatPercentage(@NotNull @Valid VatCalculationRequestDto vatCalculationRequestDto) {
        Double result = calculationService.calculateServiceTaxToApply(
                vatCalculationRequestDto.getFileId(),
                vatCalculationRequestDto.getOriginAirportId(),
                vatCalculationRequestDto.getDestinationAirportId(),
                vatCalculationRequestDto.getServiceType(),
                vatCalculationRequestDto.isSale());
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Double> calculateVatRoutePercentage(@NotNull @Valid VatCalculationRequestDto vatCalculationRequestDto) {
        Double result = calculationService.calculatePercentageOfTaxToApply(
                vatCalculationRequestDto.getOriginAirportId(),
                vatCalculationRequestDto.getDestinationAirportId(),
                vatCalculationRequestDto.getServiceType(),
                vatCalculationRequestDto.isSale());
        return ResponseEntity.ok(result);
    }
}
