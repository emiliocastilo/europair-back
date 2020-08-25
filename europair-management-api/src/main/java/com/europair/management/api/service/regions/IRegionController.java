package com.europair.management.api.service.regions;

import com.europair.management.api.dto.regions.RegionDTO;
import com.europair.management.api.dto.roles.RoleDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RequestMapping("/regions")
public interface IRegionController {

  @GetMapping("")
  @Operation(description = "Paged result of master regions", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<Page<RegionDTO>> getAllRegionsPaginated(
    @Parameter(description = "Pagination filter")final Pageable pageable) ;

  @GetMapping("/{id}")
  @Operation(description = "Retrieve master region by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<RegionDTO> getRegionById(
    @Parameter(description = "Region identifier") @NotNull @PathVariable final Long id) ;

  @PostMapping("")
  @Operation(description = "Save a new master region", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<RegionDTO> saveRegion(
    @Parameter(description = "Master Region object") @NotNull @RequestBody final RegionDTO regionDTO) ;

  @PutMapping("/{id}")
  @Operation(description = "Updates existing master region", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<RegionDTO> updateRegion(
    @Parameter(description = "Region identifier") @NotNull @PathVariable final Long id,
    @Parameter(description = "Master Region object") @NotNull @RequestBody final RegionDTO regionDTO) ;

  @DeleteMapping("/{id}")
  @Operation(description = "Deletes existing master region by identifier", security = { @SecurityRequirement(name = "bearerAuth") })
  public ResponseEntity<?> deleteRegion(
    @Parameter(description = "Region identifier") @PathVariable @NotNull final Long id) ;

}
