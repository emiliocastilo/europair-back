package com.europair.management.api.service.services;

import com.europair.management.api.dto.services.ServiceDto;
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

@RequestMapping(value = {"/services", "/external/services"})
public interface IServiceController {

  /**
   * <p>
   * Retrieves a paginated list of Service filtered by properties criteria.
   * </p>
   *
   * @param pageable pagination info
   * @param reqParam Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
   * @return Paginated list of service types
   */
  @GetMapping
  @Operation(description = "Paged result of service types with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
  ResponseEntity<Page<ServiceDto>> getServiceByFilter(
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
  ResponseEntity<ServiceDto> getServiceById(@Parameter(description = "File identifier") @NotNull @PathVariable final Long id);


  /**
   * <p>
   * Creates a new Service
   * </p>
   *
   * @param serviceDto Data of the Service to create
   * @return Data of the created service type
   */
  @PostMapping
  @Operation(description = "Save a new service type", security = {@SecurityRequirement(name = "bearerAuth")})
  ResponseEntity<ServiceDto> saveService(@Parameter(description = "Service object") @NotNull @RequestBody final ServiceDto serviceDto);


  /**
   * <p>
   * Updates service type information
   * </p>
   *
   * @param id      Unique identifier
   * @param serviceDto Updated service type data
   * @return No content
   */
  @PutMapping("/{id}")
  @Operation(description = "Updates existing service type", security = {@SecurityRequirement(name = "bearerAuth")})
  ResponseEntity<?> updateService(
    @Parameter(description = "Service identifier") @NotNull @PathVariable final Long id,
    @Parameter(description = "Service updated data") @NotNull @RequestBody final ServiceDto serviceDto);


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
  ResponseEntity<?> deleteService(@Parameter(description = "Service identifier") @PathVariable @NotNull final Long id);

}
