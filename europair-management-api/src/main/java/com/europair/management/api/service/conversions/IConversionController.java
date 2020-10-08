package com.europair.management.api.service.conversions;

import com.europair.management.api.dto.conversions.ConversionDataDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping(value = {"/conversion", "/external/conversion"})
public interface IConversionController {

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
    public ResponseEntity<List<Double>> convertData(@Parameter(description = "Data to be converted") @NotNull @RequestBody final ConversionDataDTO conversionDataDTO);
}
