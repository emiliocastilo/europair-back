package com.europair.management.api.service.files;

import com.europair.management.api.dto.files.ClientDto;
import com.europair.management.api.dto.files.FileDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Map;

@RequestMapping("/files")
public interface IFileController {

  /**
   * <p>
   * Retrieves a paginated list of File filtered by properties criteria.
   * </p>
   *
   * @param pageable pagination info
   * @param reqParam Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
   * @return Paginated list of files
   */
  @GetMapping
  @Operation(description = "Paged result of files with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
  ResponseEntity<Page<FileDTO>> getFilesByFilter(
    @Parameter(description = "Pagination filter") final Pageable pageable,
    @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);


  /**
   * <p>
   * Retrieves file data identified by id.
   * </p>
   *
   * @param id Unique identifier by id.
   * @return file data
   */
  @GetMapping("/{id}")
  @Operation(description = "Retrieve file data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
  ResponseEntity<FileDTO> getFileById(@Parameter(description = "File identifier") @NotNull @PathVariable final Long id);


  /**
   * <p>
   * Creates a new File
   * </p>
   *
   * @param fileDTO Data of the File to create
   * @return Data of the created file
   */
  @PostMapping
  @Operation(description = "Save a new file", security = {@SecurityRequirement(name = "bearerAuth")})
  ResponseEntity<FileDTO> saveFile(@Parameter(description = "File object") @NotNull @RequestBody final FileDTO fileDTO);


  /**
   * <p>
   * Updates file information
   * </p>
   *
   * @param id      Unique identifier
   * @param fileDTO Updated file data
   * @return The updated file data
   */
  @PutMapping("/{id}")
  @Operation(description = "Updates existing file", security = {@SecurityRequirement(name = "bearerAuth")})
  ResponseEntity<FileDTO> updateFile(
    @Parameter(description = "File identifier") @NotNull @PathVariable final Long id,
    @Parameter(description = "File updated data") @NotNull @RequestBody final FileDTO fileDTO);


  /**
   * <p>
   * Deletes a file by id.
   * </p>
   *
   * @param id Unique identifier
   * @return No content
   */
  @DeleteMapping("/{id}")
  @Operation(description = "Deletes existing file by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
  ResponseEntity<?> deleteFile(@Parameter(description = "File identifier") @PathVariable @NotNull final Long id);

}
