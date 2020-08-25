package com.europair.management.api.service.expedient;


import com.europair.management.api.dto.expedient.ClientDto;
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

@RequestMapping("/clients")
public interface IClientController {

    /**
     * <p>
     * Retrieves client data identified by id.
     * </p>
     *
     * @param id Unique identifier by id.
     * @return Client data
     */
    @GetMapping("/{id}")
    @Operation(description = "Retrieve master client data by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ClientDto> getClientById(@Parameter(description = "Client identifier") @NotNull @PathVariable final Long id);

    /**
     * <p>
     * Retrieves a paginated list of Client filtered by properties criteria.
     * </p>
     *
     * @param pageable pagination info
     * @param reqParam Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of client
     */
    @GetMapping
    @Operation(description = "Paged result of master client with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<ClientDto>> getClientByFilter(
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: plateNumber=JKL,CONTAINS)") @RequestParam Map<String, String> reqParam);

    /**
     * <p>
     * Creates a new Client master
     * </p>
     *
     * @param clientDto Data of the Client to create
     * @return Data of the created client
     */
    @PostMapping
    @Operation(description = "Save a new master client", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ClientDto> saveClient(@Parameter(description = "Master Client object") @NotNull @RequestBody final ClientDto clientDto);

    /**
     * <p>
     * Updated master client information
     * </p>
     *
     * @param id        Unique identifier
     * @param clientDto Updated client data
     * @return The updated master client
     */
    @PutMapping("/{id}")
    @Operation(description = "Updates existing master client", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<ClientDto> updateClient(
            @Parameter(description = "Client identifier") @NotNull @PathVariable final Long id,
            @Parameter(description = "Master Client updated data") @NotNull @RequestBody final ClientDto clientDto);

    /**
     * <p>
     * Deletes a master client by id.
     * </p>
     *
     * @param id Unique identifier
     * @return No content
     */
    @DeleteMapping("/{id}")
    @Operation(description = "Deletes existing master client by identifier", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<?> deleteClient(@Parameter(description = "Client identifier") @PathVariable @NotNull final Long id);

}
