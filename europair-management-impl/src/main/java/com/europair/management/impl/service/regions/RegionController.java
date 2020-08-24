package com.europair.management.impl.service.regions;

import com.europair.management.api.dto.regions.RegionDTO;
import com.europair.management.api.service.regions.IRegionController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/regions")
public class RegionController implements IRegionController {

  private final IRegionService regionService;

  @GetMapping("")
  @Override
  public ResponseEntity<Page<RegionDTO>> getAllRegionsPaginated(Pageable pageable) {
    final Page<RegionDTO> pageRegionsDTO = regionService.findAllPaginated(pageable);
    return ResponseEntity.ok().body(pageRegionsDTO);
  }

  @GetMapping("/{id}")
  @Override
  public ResponseEntity<RegionDTO> getRegionById(@NotNull Long id) {
    final RegionDTO regionDTO = regionService.findById(id);
    return ResponseEntity.ok().body(regionDTO);
  }

  @PostMapping("")
  @Override
  public ResponseEntity<RegionDTO> saveRegion(@NotNull RegionDTO regionDTO) {

    final RegionDTO regionDTOSaved = regionService.saveRegion(regionDTO);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(regionDTOSaved.getId())
      .toUri();

    return ResponseEntity.created(location).body(regionDTOSaved);

  }

  @PutMapping("/{id}")
  @Override
  public ResponseEntity<RegionDTO> updateRegion(@NotNull Long id, @NotNull RegionDTO regionDTO) {
    final RegionDTO regionDTOUpdated = regionService.updateRegion(id, regionDTO);
    return ResponseEntity.ok().body(regionDTOUpdated);
  }

  @DeleteMapping("/{id}")
  @Override
  public ResponseEntity<?> deleteRegion(@NotNull Long id) {
    regionService.deleteRegion(id);
    return ResponseEntity.noContent().build();
  }
}
