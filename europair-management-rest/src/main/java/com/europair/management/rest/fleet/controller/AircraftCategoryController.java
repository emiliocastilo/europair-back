package com.europair.management.rest.fleet.controller;

import com.europair.management.rest.common.utils.Utils;
import com.europair.management.rest.fleet.service.AircraftCategoryService;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.fleet.dto.AircraftCategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/aircraft-categories")
public class AircraftCategoryController {

    private final String SUBCATEGORY_ENDPOINT = "/{categoryId}/subcategories";

    @Autowired
    private AircraftCategoryService aircraftCategoryService;

    /**
     * <p>Creates a new Aircraft Category master</p>
     *
     * @param aircraftCategoryDto Data of the category to create
     * @return Data of the created category
     */
    @PostMapping
    @Operation(description = "Save new master aircraft category", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<AircraftCategoryDto> saveAircraftCategory(
            @Parameter(description = "Master Aircraft category data") @NotNull @RequestBody final AircraftCategoryDto aircraftCategoryDto) {
        final AircraftCategoryDto dtoSaved = aircraftCategoryService.saveAircraftCategory(aircraftCategoryDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(dtoSaved);
    }

    /**
     * <p>Updates master aircraft category</p>
     *
     * @param id                  Unique identifier
     * @param aircraftCategoryDto Updated category data
     * @return The updated category data
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing aircraft category", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<AircraftCategoryDto> updateAircraftCategory(
            @Parameter(description = "Aircraft Category identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master Aircraft Category updated data") @NotNull @RequestBody final AircraftCategoryDto aircraftCategoryDto) {
        final AircraftCategoryDto dtoSaved = aircraftCategoryService.updateAircraftCategory(id, aircraftCategoryDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoSaved.getId())
                .toUri();

        return ResponseEntity.ok(dtoSaved);
    }

    /**
     * <p>Deletes a master aircraft category by id</p>
     *
     * @param id Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Deletes existing master aircraft category by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<?> deleteAircraftCategory(@Parameter(description = "Category identifier") @PathVariable @NotNull final Long id) {
        aircraftCategoryService.deleteAircraftCategory(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * <p>Retrieves aircraft category data identified by id.</p>
     *
     * @param id Unique identifier
     * @return Category data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieves master aircraft category data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<AircraftCategoryDto> findById(@Parameter(description = "Category identifier") @PathVariable @NotNull final Long id) {
        final AircraftCategoryDto aircraftCategoryDto = aircraftCategoryService.findById(id);

        return ResponseEntity.ok(aircraftCategoryDto);
    }

    /**
     * <p>Retrieves a paginated list of Aircraft Category.</p>
     *
     * @param pageable pagination info
     * @param reqParam Map of filter params, values and operators. (pe: filter_name=AS,CONTAINS)
     * @return Paginated list of categories
     */
    @GetMapping
    @Operation(description = "Paged result of master aircraft", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<Page<AircraftCategoryDto>> findAllPaginated(
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: filter_name=AS,CONTAINS)") @RequestParam Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<AircraftCategoryDto> aircraftCategoryPage = aircraftCategoryService.findAllPaginated(criteria, pageable);

        return ResponseEntity.ok(aircraftCategoryPage);
    }


    /*
        SUBCATEGORY ENDPOINTS
    */

    /**
     * <p>Creates a new Subcategory</p>
     *
     * @param categoryId          Parent Category identifier
     * @param aircraftCategoryDto Data of the category to create
     * @return Data of the created category
     */
    @PostMapping(SUBCATEGORY_ENDPOINT)
    @Operation(description = "Save new subcategory", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<AircraftCategoryDto> saveAircraftSubcategory(
            @Parameter(description = "Parent category id") @NotNull @PathVariable final Long categoryId,
            @Parameter(description = "Master Aircraft category data") @NotNull @RequestBody final AircraftCategoryDto aircraftCategoryDto) {

        final AircraftCategoryDto dtoSaved = aircraftCategoryService.saveAircraftSubcategory(categoryId, aircraftCategoryDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoryId, dtoSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(dtoSaved);
    }

    /**
     * <p>Updates aircraft subcategory</p>
     *
     * @param categoryId          Parent Category identifier
     * @param id                  Unique identifier
     * @param aircraftCategoryDto Updated subcategory data
     * @return The updated subcategory data
     */
    @PutMapping(SUBCATEGORY_ENDPOINT + "/{id}")
    @Operation(description = "Updates existing aircraft subcategory", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<AircraftCategoryDto> updateAircraftCategory(
            @Parameter(description = "Parent category id") @NotNull @PathVariable final Long categoryId,
            @Parameter(description = "Aircraft Category identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master Aircraft Category updated data") @NotNull @RequestBody final AircraftCategoryDto aircraftCategoryDto) {
        final AircraftCategoryDto dtoSaved = aircraftCategoryService.updateAircraftSubcategory(categoryId, id, aircraftCategoryDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoryId, dtoSaved.getId())
                .toUri();

        return ResponseEntity.ok(dtoSaved);
    }

    /**
     * <p>Deletes a aircraft subcategory by id</p>
     *
     * @param categoryId Parent Category identifier
     * @param id         Unique identifier
     * @return No content
     */
    @DeleteMapping(SUBCATEGORY_ENDPOINT + "/{id}")
    @Operation(description = "Deletes existing aircraft subcategory by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<?> deleteAircraftCategory(
            @Parameter(description = "Parent category id") @NotNull @PathVariable final Long categoryId,
            @Parameter(description = "Category identifier") @PathVariable @NotNull final Long id) {
        aircraftCategoryService.deleteAircraftSubcategory(categoryId, id);

        return ResponseEntity.noContent().build();
    }

    /**
     * <p>Retrieves aircraft subcategory data identified by id.</p>
     *
     * @param categoryId Parent Category identifier
     * @param id         Unique identifier
     * @return Subcategory data
     */
    @GetMapping(SUBCATEGORY_ENDPOINT + "/{id}")
    @Operation(description = "Retrieves aircraft subcategory data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<AircraftCategoryDto> findSubcategoryById(
            @Parameter(description = "Parent category id") @NotNull @PathVariable final Long categoryId,
            @Parameter(description = "Category identifier") @PathVariable @NotNull final Long id) {
        final AircraftCategoryDto aircraftCategoryDto = aircraftCategoryService.findSubcategoryById(categoryId, id);

        return ResponseEntity.ok(aircraftCategoryDto);
    }

    /**
     * <p>Retrieves a paginated list of Aircraft subcategories.</p>
     *
     * @param categoryId Parent Category identifier
     * @param pageable   pagination info
     * @param reqParam   Map of filter params, values and operators. (pe: filter_name=AS,CONTAINS)
     * @return Paginated list of categories
     */
    @GetMapping(SUBCATEGORY_ENDPOINT)
    @Operation(description = "Paged result of master aircraft", security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<Page<AircraftCategoryDto>> findAllPaginated(
            @Parameter(description = "Parent category id") @NotNull @PathVariable final Long categoryId,
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: filter_name=AS,CONTAINS)") @RequestParam Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<AircraftCategoryDto> aircraftCategoryPage =
                aircraftCategoryService.findAllSubcategoriesPaginated(categoryId, criteria, pageable);

        return ResponseEntity.ok(aircraftCategoryPage);
    }


}
