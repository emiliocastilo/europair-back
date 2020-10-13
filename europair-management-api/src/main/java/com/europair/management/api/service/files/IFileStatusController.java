package com.europair.management.api.service.files;


import com.europair.management.api.dto.files.FileStatusDto;
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

@RequestMapping(value = {"/fileStatus", "/external/fileStatus"})
public interface IFileStatusController {

    /**
     * <p>
     * Retrieves fileStatus data identified by id.
     * </p>
     *
     * @param id Unique identifier by id.
     * @return FileStatus data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master fileStatus data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<FileStatusDto> getFileStatusById(@Parameter(description = "FileStatus identifier") @NotNull @PathVariable final Long id);

    /**
     * <p>
     * Retrieves a paginated list of FileStatus filtered by properties criteria.
     * </p>
     *
     * @param pageable pagination info
     * @param reqParam Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of fileStatus
     */
    @GetMapping
    @Operation(description = "Paged result of master fileStatus with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<FileStatusDto>> getFileStatusByFilter(
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new FileStatus master
     * </p>
     *
     * @param fileStatusDto Data of the FileStatus to create
     * @return Data of the created fileStatus
     */
    @PostMapping
    @Operation(description = "Save a new master fileStatus", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<FileStatusDto> saveFileStatus(@Parameter(description = "Master FileStatus object") @NotNull @RequestBody final FileStatusDto fileStatusDto);

    /**
     * <p>
     * Updated master fileStatus information
     * </p>
     *
     * @param id                 Unique identifier
     * @param fileStatusDto Updated fileStatus data
     * @return The updated master fileStatus
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master fileStatus", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<FileStatusDto> updateFileStatus(
            @Parameter(description = "FileStatus identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master FileStatus updated data") @NotNull @RequestBody final FileStatusDto fileStatusDto);

    /**
     * <p>
     * Deletes a master fileStatus by id.
     * </p>
     *
     * @param id Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Deletes existing master fileStatus by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteFileStatus(@Parameter(description = "FileStatus identifier") @PathVariable @NotNull final Long id);

}
