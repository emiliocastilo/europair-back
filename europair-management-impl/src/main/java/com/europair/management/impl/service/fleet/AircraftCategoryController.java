package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftCategoryDto;
import com.europair.management.api.service.fleet.IAircraftCategoryController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Map;

@RestController
@Slf4j
public class AircraftCategoryController implements IAircraftCategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AircraftCategoryController.class);

    private final String SUBCATEGORY_ENDPOINT = "/{categoryId}/subcategories";

    @Autowired
    private AircraftCategoryService aircraftCategoryService;

    @Override
    public ResponseEntity<AircraftCategoryDto> saveAircraftCategory(@NotNull final AircraftCategoryDto aircraftCategoryDto) {
        LOGGER.debug("[AircraftCategoryController] - Starting method [saveAircraftCategory] with input: aircraftCategoryDto={}", aircraftCategoryDto);
        final AircraftCategoryDto dtoSaved = aircraftCategoryService.saveAircraftCategory(aircraftCategoryDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoSaved.getId())
                .toUri();

        LOGGER.debug("[AircraftCategoryController] - Ending method [saveAircraftCategory] with return: {}", dtoSaved);
        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<AircraftCategoryDto> updateAircraftCategory(@NotNull final Long id, @NotNull final AircraftCategoryDto aircraftCategoryDto) {
        LOGGER.debug("[AircraftCategoryController] - Starting method [updateAircraftCategory] with input: id={}, aircraftCategoryDto={}", id, aircraftCategoryDto);
        final AircraftCategoryDto dtoSaved = aircraftCategoryService.updateAircraftCategory(id, aircraftCategoryDto);
        LOGGER.debug("[AircraftCategoryController] - Ending method [updateAircraftCategory] with return: {}", dtoSaved);
        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteAircraftCategory(@NotNull final Long id) {
        LOGGER.debug("[AircraftCategoryController] - Starting method [deleteAircraftCategory] with input: id={}", id);
        aircraftCategoryService.deleteAircraftCategory(id);
        LOGGER.debug("[AircraftCategoryController] - Ending method [deleteAircraftCategory] with no return.");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<AircraftCategoryDto> findById(@NotNull final Long id) {
        LOGGER.debug("[AircraftCategoryController] - Starting method [findById] with input: id={}", id);
        final AircraftCategoryDto aircraftCategoryDto = aircraftCategoryService.findById(id);
        LOGGER.debug("[AircraftCategoryController] - Ending method [findById] with return: {}", aircraftCategoryDto);
        return ResponseEntity.ok(aircraftCategoryDto);
    }

    @Override
    public ResponseEntity<Page<AircraftCategoryDto>> findAllPaginated(final Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[AircraftCategoryController] - Starting method [findAllPaginated] with input: pageable={}, reqParam={}", pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<AircraftCategoryDto> aircraftCategoryPage = aircraftCategoryService.findAllPaginated(criteria, pageable);
        LOGGER.debug("[AircraftCategoryController] - Ending method [findAllPaginated] with return: {}", aircraftCategoryPage);
        return ResponseEntity.ok(aircraftCategoryPage);
    }


    /*
        SUBCATEGORY ENDPOINTS
    */

    @Override
    public ResponseEntity<AircraftCategoryDto> saveAircraftSubcategory(final Long categoryId, @NotNull final AircraftCategoryDto aircraftCategoryDto) {
        LOGGER.debug("[AircraftCategoryController] - Starting method [saveAircraftSubcategory] with input: categoryId={}, aircraftCategoryDto={}",
                categoryId, aircraftCategoryDto);
        final AircraftCategoryDto dtoSaved = aircraftCategoryService.saveAircraftSubcategory(categoryId, aircraftCategoryDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoryId, dtoSaved.getId())
                .toUri();
        LOGGER.debug("[AircraftCategoryController] - Ending method [saveAircraftSubcategory] with return: {}", dtoSaved);
        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<AircraftCategoryDto> updateAircraftCategory(@NotNull final Long categoryId, @NotNull final Long id,
                                                                      @NotNull final AircraftCategoryDto aircraftCategoryDto) {
        LOGGER.debug("[AircraftCategoryController] - Starting method [updateAircraftCategory] with input: categoryId={}, id={}, aircraftCategoryDto={}",
                categoryId, id, aircraftCategoryDto);
        final AircraftCategoryDto dtoSaved = aircraftCategoryService.updateAircraftSubcategory(categoryId, id, aircraftCategoryDto);
        LOGGER.debug("[AircraftCategoryController] - Ending method [updateAircraftCategory] with return: {}", dtoSaved);
        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteAircraftCategory(@NotNull final Long categoryId, @NotNull final Long id) {
        LOGGER.debug("[AircraftCategoryController] - Starting method [deleteAircraftCategory] with input: categoryId={}, id={}", categoryId, id);
        aircraftCategoryService.deleteAircraftSubcategory(categoryId, id);
        LOGGER.debug("[AircraftCategoryController] - Ending method [deleteAircraftCategory] with no return.");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<AircraftCategoryDto> findSubcategoryById(@NotNull final Long categoryId, @NotNull final Long id) {
        LOGGER.debug("[AircraftCategoryController] - Starting method [findSubcategoryById] with input: categoryId={}, id={}", categoryId, id);
        final AircraftCategoryDto aircraftCategoryDto = aircraftCategoryService.findSubcategoryById(categoryId, id);
        LOGGER.debug("[AircraftCategoryController] - Ending method [findSubcategoryById] with return: {}", aircraftCategoryDto);
        return ResponseEntity.ok(aircraftCategoryDto);
    }

    @Override
    public ResponseEntity<Page<AircraftCategoryDto>> findAllPaginated(@NotNull final Long categoryId, final Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[AircraftCategoryController] - Starting method [findAllPaginated] with input: categoryId={}, pageable={}, reqParam={}",
                categoryId, pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<AircraftCategoryDto> aircraftCategoryPage =
                aircraftCategoryService.findAllSubcategoriesPaginated(categoryId, criteria, pageable);
        LOGGER.debug("[AircraftCategoryController] - Ending method [findAllPaginated] with return: {}", aircraftCategoryPage);
        return ResponseEntity.ok(aircraftCategoryPage);
    }

}
