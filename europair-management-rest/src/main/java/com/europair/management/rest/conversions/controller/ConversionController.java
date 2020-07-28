package com.europair.management.rest.conversions.controller;

import com.europair.management.rest.conversions.service.ConversionService;
import com.europair.management.rest.model.conversions.dto.ConversionDataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/conversion")
public class ConversionController {

    private final ConversionService conversionService;

    /**
     * <p>
     * Convert data between different units.
     * </p>
     *
     * @param conversionDataDTO
     * @return Coverted data in given units.
     */

    @PostMapping("")
    public ResponseEntity<List<Double>> convertData(@RequestBody final ConversionDataDTO conversionDataDTO) {

        final List<Double> convertedData = conversionService.convertData(conversionDataDTO);

        return ResponseEntity.ok().body(convertedData);
    }
}
