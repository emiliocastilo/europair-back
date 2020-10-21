package com.europair.management.api.service.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = {"/enums", "/external/enums"})
public interface IEnumValuesController {

    @GetMapping("/file-states")
    @Operation(description = "Get file states enum values", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<List<String>> getFileStatesValues();

    @GetMapping("/route-states")
    @Operation(description = "Get route states enum values", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<List<String>> getRouteStatesValues();

    @GetMapping("/contributions-states")
    @Operation(description = "Get contributions states enum values", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<List<String>> getContributionStatesValues();

}
