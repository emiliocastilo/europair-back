package com.europair.management.api.service.files;


import com.europair.management.api.dto.files.ContactDto;
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

@RequestMapping(value = {"/contacts", "/external/contacts"})
public interface IContactController {

    /**
     * <p>
     * Retrieves contact data identified by id.
     * </p>
     *
     * @param id Unique identifier by id.
     * @return Contact data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master contact data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ContactDto> getContactById(@Parameter(description = "Contact identifier") @NotNull @PathVariable final Long id);

    /**
     * <p>
     * Retrieves a paginated list of Contact filtered by properties criteria.
     * </p>
     *
     * @param pageable pagination info
     * @param reqParam Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of contact
     */
    @GetMapping
    @Operation(description = "Paged result of master contact with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<ContactDto>> getContactByFilter(
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new Contact master
     * </p>
     *
     * @param contactDto Data of the Contact to create
     * @return Data of the created contact
     */
    @PostMapping
    @Operation(description = "Save a new master contact", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ContactDto> saveContact(@Parameter(description = "Master Contact object") @NotNull @RequestBody final ContactDto contactDto);

    /**
     * <p>
     * Updated master contact information
     * </p>
     *
     * @param id         Unique identifier
     * @param contactDto Updated contact data
     * @return The updated master contact
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master contact", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ContactDto> updateContact(
            @Parameter(description = "Contact identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master Contact updated data") @NotNull @RequestBody final ContactDto contactDto);

    /**
     * <p>
     * Deletes a master contact by id.
     * </p>
     *
     * @param id Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Deletes existing master contact by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteContact(@Parameter(description = "Contact identifier") @PathVariable @NotNull final Long id);

}
