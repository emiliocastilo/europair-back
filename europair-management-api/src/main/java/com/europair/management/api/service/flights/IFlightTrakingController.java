package com.europair.management.api.service.flights;

import com.europair.management.api.dto.flights.FlightDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Map;

@RequestMapping(value = {"/flights", "/external/flights"})
public interface IFlightTrakingController {

    /**
     * <p>
     * Retrieves a paginated list of Flight filtered by properties criteria.
     * </p>
     *
     * @param pageable pagination info
     * @param reqParam Map of filter params, values and operators. (pe: filter_description=asd,CONTAINS)
     * @return Paginated list of flights
     */
    @GetMapping
    @Operation(description = "Paged result of flights with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<FlightDTO>> getAllFlightsPaginated(
      @Parameter(description = "Pagination filter") final Pageable pageable,
      @Parameter(description = "Map of properties to filter with value and operator, (pe: filter_description=asd,CONTAINS)") @RequestParam
    Map<String, String> reqParam);


    /**
     * <p>
     * Retrieves flight data identified by id.
     * </p>
     *
     * @param id Unique identifier by id.
     * @return Flight data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve flight data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<FlightDTO> getFlightById(@Parameter(description = "Flight identifier") @NotNull @PathVariable final Long id);

}
