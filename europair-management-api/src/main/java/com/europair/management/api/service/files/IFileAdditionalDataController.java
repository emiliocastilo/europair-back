package com.europair.management.api.service.files;

import com.europair.management.api.dto.files.FileAdditionalDataDto;
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@RequestMapping(value = {"/files/{fileId}/additional-data", "/external/files/{fileId}/additional-data"})
public interface IFileAdditionalDataController {


    /**
     * <p>
     * Retrieves fileAdditionalData data identified by id.
     * </p>
     *
     * @param fileId File identifier
     * @param id     Unique identifier by id.
     * @return FileAdditionalData data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master fileAdditionalData data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<FileAdditionalDataDto> getFileAdditionalDataById(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "FileAdditionalData identifier") @NotNull @PathVariable final Long id
    );

    /**
     * <p>
     * Retrieves a paginated list of FileAdditionalData filtered by properties criteria.
     * </p>
     *
     * @param fileId   File identifier
     * @param pageable pagination info
     * @param reqParam Map of filter params, values and operators. (pe: filter_luggage=JKL,CONTAINS)
     * @return Paginated list of fileAdditionalData
     */
    @GetMapping
    @Operation(description = "Paged result of master fileAdditionalData with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<FileAdditionalDataDto>> getFileAdditionalDataByFilter(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: filter_luggage=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new FileAdditionalData entity
     * </p>
     *
     * @param fileId                File identifier
     * @param fileAdditionalDataDto Data of the FileAdditionalData to create
     * @return Data of the created fileAdditionalData
     */
    @PostMapping
    @Operation(description = "Save a new master fileAdditionalData", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<FileAdditionalDataDto> saveFileAdditionalData(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "Master FileAdditionalData object") @NotNull @Valid @RequestBody final FileAdditionalDataDto fileAdditionalDataDto);

    /**
     * <p>
     * Updated master fileAdditionalData information
     * </p>
     *
     * @param fileId                File identifier
     * @param id                    Unique identifier
     * @param fileAdditionalDataDto Updated fileAdditionalData data
     * @return The updated master fileAdditionalData
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master fileAdditionalData", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> updateFileAdditionalData(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "FileAdditionalData identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master FileAdditionalData updated data") @NotNull @Valid @RequestBody final FileAdditionalDataDto fileAdditionalDataDto);

    /**
     * <p>
     * Deletes a master fileAdditionalData by id.
     * </p>
     *
     * @param fileId File identifier
     * @param id     Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Delete existing master fileAdditionalData by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteFileAdditionalData(
            @Parameter(description = "File identifier") @NotNull @PathVariable final Long fileId,
            @Parameter(description = "FileAdditionalData identifier") @PathVariable @NotNull final Long id);

}
