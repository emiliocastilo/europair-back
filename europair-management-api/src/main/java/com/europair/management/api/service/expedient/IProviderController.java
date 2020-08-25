package com.europair.management.api.service.expedient;


import com.europair.management.api.dto.expedient.ProviderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.util.Map;

@RequestMapping("/providers")
public interface IProviderController {

    /**
     * <p>
     * Retrieves provider data identified by id.
     * </p>
     *
     * @param id Unique identifier by id.
     * @return Provider data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master provider data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ProviderDto> getProviderById(@Parameter(description = "Provider identifier") @NotNull @PathVariable final Long id);

    /**
     * <p>
     * Retrieves a paginated list of Provider filtered by properties criteria.
     * </p>
     *
     * @param pageable pagination info
     * @param reqParam Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of provider
     */
    @GetMapping
    @Operation(description = "Paged result of master provider with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<ProviderDto>> getProviderByFilter(
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new Provider master
     * </p>
     *
     * @param providerDto Data of the Provider to create
     * @return Data of the created provider
     */
    @PostMapping
    @Operation(description = "Save a new master provider", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ProviderDto> saveProvider(@Parameter(description = "Master Provider object") @NotNull @RequestBody final ProviderDto providerDto);

    /**
     * <p>
     * Updated master provider information
     * </p>
     *
     * @param id          Unique identifier
     * @param providerDto Updated provider data
     * @return The updated master provider
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master provider", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ProviderDto> updateProvider(
            @Parameter(description = "Provider identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master Provider updated data") @NotNull @RequestBody final ProviderDto providerDto);

    /**
     * <p>
     * Deletes a master provider by id.
     * </p>
     *
     * @param id Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Deletes existing master provider by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteProvider(@Parameter(description = "Provider identifier") @PathVariable @NotNull final Long id);

}
