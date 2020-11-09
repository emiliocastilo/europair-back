package com.europair.management.impl.service.files;

import com.europair.management.api.dto.files.ClientDto;
import com.europair.management.api.service.files.IClientController;
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
public class ClientController implements IClientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private IClientService clientService;

    public ResponseEntity<ClientDto> getClientById(@NotNull final Long id) {
        LOGGER.debug("[ClientController] - Starting method [getClientById] with input: id={}", id);
        final ClientDto clientDto = clientService.findById(id);
        LOGGER.debug("[ClientController] - Ending method [getClientById] with return: {}", clientDto);
        return ResponseEntity.ok(clientDto);
    }

    public ResponseEntity<Page<ClientDto>> getClientByFilter(Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[ClientController] - Starting method [getClientByFilter] with input: pageable={}, reqParam={}", pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<ClientDto> clientDtoPage = clientService.findAllPaginatedByFilter(pageable, criteria);
        LOGGER.debug("[ClientController] - Ending method [getClientByFilter] with return: {}", clientDtoPage);
        return ResponseEntity.ok(clientDtoPage);
    }

    public ResponseEntity<ClientDto> saveClient(@NotNull final ClientDto clientDto) {
        LOGGER.debug("[ClientController] - Starting method [saveClient] with input: clientDto={}", clientDto);

        final ClientDto clientDtoSaved = clientService.saveClient(clientDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clientDtoSaved.getId())
                .toUri();

        LOGGER.debug("[ClientController] - Ending method [saveClient] with return: {}", clientDtoSaved);
        return ResponseEntity.created(location).body(clientDtoSaved);

    }

    public ResponseEntity<ClientDto> updateClient(@NotNull final Long id, @NotNull final ClientDto clientDto) {
        LOGGER.debug("[ClientController] - Starting method [updateClient] with input: id={}, clientDto={}", id, clientDto);

        final ClientDto clientDtoSaved = clientService.updateClient(id, clientDto);
        LOGGER.debug("[ClientController] - Ending method [updateClient] with return: {}", clientDtoSaved);
        return ResponseEntity.ok().body(clientDtoSaved);
    }

    public ResponseEntity<?> deleteClient(@NotNull final Long id) {
        LOGGER.debug("[ClientController] - Starting method [deleteClient] with input: id={}", id);
        clientService.deleteClient(id);
        LOGGER.debug("[ClientController] - Ending method [deleteClient] with no return.");
        return ResponseEntity.noContent().build();

    }

}
