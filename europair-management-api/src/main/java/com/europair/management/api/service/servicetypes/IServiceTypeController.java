package com.europair.management.api.service.servicetypes;

import com.europair.management.api.dto.servicetypes.ServiceTypeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Map;

@RequestMapping("/serviceTypes")
public interface IServiceTypeController {

  /**
   * <p>
   * Retrieves a paginated list of ServiceType filtered by properties criteria.
   * </p>
   *
   * @param pageable pagination info
   * @param reqParam Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
   * @return Paginated list of service types
   */
  @GetMapping
  @Operation(description = "Paged result of service types with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
  ResponseEntity<Page<ServiceTypeDTO>> getServiceTypeByFilter(
    @Parameter(description = "Pagination filter") final Pageable pageable,
    @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);


  /**
   * <p>
   * Retrieves service type data identified by id.
   * </p>
   *
   * @param id Unique identifier by id.
   * @return service type data
   */
  @GetMapping("/{id}")
  @Operation(description = "Retrieve service type data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
  ResponseEntity<ServiceTypeDTO> getServiceTypeById(@Parameter(description = "File identifier") @NotNull @PathVariable final Long id);


  /**
   * <p>
   * Creates a new ServiceType
   * </p>
   *
   * @param serviceTypeDTO Data of the ServiceType to create
   * @return Data of the created service type
   */
  @PostMapping
  @Operation(description = "Save a new service type", security = {@SecurityRequirement(name = "bearerAuth")})
  ResponseEntity<ServiceTypeDTO> saveServiceType(@Parameter(description = "ServiceType object") @NotNull @RequestBody final ServiceTypeDTO serviceTypeDTO);


  /**
   * <p>
   * Updates service type information
   * </p>
   *
   * @param id      Unique identifier
   * @param serviceTypeDTO Updated service type data
   * @return The updated service type data
   */
  @PutMapping("/{id}")
  @Operation(description = "Updates existing service type", security = {@SecurityRequirement(name = "bearerAuth")})
  ResponseEntity<ServiceTypeDTO> updateServiceType(
    @Parameter(description = "ServiceType identifier") @NotNull @PathVariable final Long id,
    @Parameter(description = "ServiceType updated data") @NotNull @RequestBody final ServiceTypeDTO serviceTypeDTO);


  /**
   * <p>
   * Deletes a service type by id.
   * </p>
   *
   * @param id Unique identifier
   * @return No content
   */
  @DeleteMapping("/{id}")
  @Operation(description = "Deletes existing service type by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
  ResponseEntity<?> deleteServiceType(@Parameter(description = "ServiceType identifier") @PathVariable @NotNull final Long id);

}
