package com.europair.management.api.service.calculation;

import com.europair.management.api.dto.calculation.VatCalculationRequestDto;
import com.europair.management.api.dto.calculation.VatCalculationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequestMapping(value = {"/calculations", "/external/calculations"})
public interface ICalculationController {

    /**
     * <p>
     * Calculates the vat percentage value and percentage of the route where applies
     * (isn't 100% if the route goes through international waters)
     * </p>
     *
     * @param vatCalculationRequestDto Request data to make the calculations
     * @return Vat to apply and percentage of the route
     */
    @GetMapping("/vat")
    @Operation(description = "Calculates the vat amount and percentage of the route where applies", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<VatCalculationResponseDto> calculateVat(
            @Parameter(description = "Request data to make the calculations") @NotNull @Valid final VatCalculationRequestDto vatCalculationRequestDto);

    /**
     * <p>
     * Calculates the vat percentage
     * </p>
     *
     * @param vatCalculationRequestDto Request data to make the calculations
     * @return Vat percentage to apply
     */
    @GetMapping("/vat/percentage")
    @Operation(description = "Calculates the vat percentage", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Double> calculateVatPercentage(
            @Parameter(description = "Request data to make the calculations") @NotNull @Valid final VatCalculationRequestDto vatCalculationRequestDto);


    /**
     * <p>
     * Calculates the percentage of the route where applies
     * (isn't 100% if the route goes through international waters)
     * </p>
     *
     * @param vatCalculationRequestDto Request data to make the calculations
     * @return Percentage of the route where taxes apply
     */
    @GetMapping("/vat/route-percentage")
    @Operation(description = "Calculates the percentage of the route where applies", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Double> calculateVatRoutePercentage(
            @Parameter(description = "Request data to make the calculations") @NotNull @Valid final VatCalculationRequestDto vatCalculationRequestDto);
}
