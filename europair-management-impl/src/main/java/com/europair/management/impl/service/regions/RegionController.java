package com.europair.management.impl.service.regions;

import com.europair.management.api.dto.regions.RegionDTO;
import com.europair.management.api.service.regions.IRegionController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RegionController implements IRegionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegionController.class);

    private final IRegionService regionService;

    @Override
    public ResponseEntity<Page<RegionDTO>> getAllRegionsPaginated(Pageable pageable) {
        LOGGER.debug("[RegionController] - Starting method [getAllRegionsPaginated] with input: pageable={}", pageable);
        final Page<RegionDTO> pageRegionsDTO = regionService.findAllPaginated(pageable);
        LOGGER.debug("[RegionController] - Ending method [getAllRegionsPaginated] with return: {}", pageRegionsDTO);
        return ResponseEntity.ok().body(pageRegionsDTO);
    }

    @Override
    public ResponseEntity<RegionDTO> getRegionById(@NotNull Long id) {
        LOGGER.debug("[RegionController] - Starting method [getRegionById] with input: id={}", id);
        final RegionDTO regionDTO = regionService.findById(id);
        LOGGER.debug("[RegionController] - Ending method [getRegionById] with return: {}", regionDTO);
        return ResponseEntity.ok().body(regionDTO);
    }

    @PostMapping("")
    @Override
    public ResponseEntity<RegionDTO> saveRegion(@NotNull RegionDTO regionDTO) {
        LOGGER.debug("[RegionController] - Starting method [saveRegion] with input: regionDTO={}", regionDTO);
        final RegionDTO regionDTOSaved = regionService.saveRegion(regionDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(regionDTOSaved.getId())
                .toUri();
        LOGGER.debug("[RegionController] - Ending method [saveRegion] with return: {}", regionDTOSaved);
        return ResponseEntity.created(location).body(regionDTOSaved);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<RegionDTO> updateRegion(@NotNull Long id, @NotNull RegionDTO regionDTO) {
        LOGGER.debug("[RegionController] - Starting method [updateRegion] with input: id={}, regionDTO={}", id, regionDTO);
        final RegionDTO regionDTOUpdated = regionService.updateRegion(id, regionDTO);
        LOGGER.debug("[RegionController] - Ending method [updateRegion] with return: {}", regionDTOUpdated);
        return ResponseEntity.ok().body(regionDTOUpdated);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> deleteRegion(@NotNull Long id) {
        LOGGER.debug("[RegionController] - Starting method [deleteRegion] with input: id={}", id);
        regionService.deleteRegion(id);
        LOGGER.debug("[RegionController] - Ending method [deleteRegion] with no return.");
        return ResponseEntity.noContent().build();
    }
}
