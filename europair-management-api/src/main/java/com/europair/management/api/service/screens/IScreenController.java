package com.europair.management.api.service.screens;

import com.europair.management.api.dto.screens.ScreenDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.util.Map;

@RequestMapping("/screens")
public interface IScreenController {

    /**
     * <p>
     * Retrieves a paginated list of Screen filtered by properties criteria.
     * </p>
     *
     * @param pageable pagination info
     * @param reqParam Map of filter params, values and operators. (pe: filter_name=abc,CONTAINS)
     * @return Paginated list of screen
     */
    @GetMapping
    @Operation(description = "Paged result of master screen with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<Page<ScreenDTO>> getAllScreensPaginated(
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: filter_name=abc,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Retrieves screen data identified by id.
     * </p>
     *
     * @param id Unique identifier by id.
     * @return Screen data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master screen data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<ScreenDTO> getScreenById(@Parameter(description = "Screen identifier") @NotNull @PathVariable final Long id);

}
