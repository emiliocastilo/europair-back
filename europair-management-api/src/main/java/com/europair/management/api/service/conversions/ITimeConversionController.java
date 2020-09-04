package com.europair.management.api.service.conversions;

import com.europair.management.api.dto.conversions.TransformUTCTimeFromToDTO;
import com.europair.management.api.enums.UTCEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@RequestMapping("/conversions/time")
public interface ITimeConversionController {


    /**
     * Operation to transform date time from an utc indicator to another
     * @param transformUTCTimeFromToDTO request body to retrieve the parameters
     * @return <code>String</code> new time in String
     */
    @PostMapping("")
    @Operation(description = "Transforms the input date time in UTC X (fromUTCIndicator) into UTC Y (toUTCIndicator)", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<String> transformTimeFromUTCIndicatorToUTCIndicator(
            @Parameter(description = "Request objetc to transform time in UTC from UTC X to UTC Y") @NotNull @RequestBody TransformUTCTimeFromToDTO transformUTCTimeFromToDTO);


    @GetMapping("")
    @Operation(description = "Get all the UTC options in the world", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<String> getUTCEnum();

}
