package com.europair.management.rest.conversions.controller;

import com.europair.management.api.dto.conversions.ConversionDataDTO;
import com.europair.management.rest.conversions.service.ConversionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/conversion")
public class ConversionController {

    private final ConversionService conversionService;

    /**
     * <p>
     * Converts data between different units.
     * </p>
     *
     * @param conversionDataDTO
     * @return Coverted data in given units.
     */

    @PostMapping("")
    @Operation(description = "Converts data between different units.", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<List<Double>> convertData(@Parameter(description = "Data to be converted") @NotNull @RequestBody final ConversionDataDTO conversionDataDTO) {

        final List<Double> convertedData = conversionService.convertData(conversionDataDTO);

        return ResponseEntity.ok().body(convertedData);
    }
}
