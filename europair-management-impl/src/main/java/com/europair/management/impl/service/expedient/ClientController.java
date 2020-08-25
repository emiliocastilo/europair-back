package com.europair.management.impl.service.expedient;

import com.europair.management.api.dto.expedient.ClientDto;
import com.europair.management.api.service.expedient.IClientController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.extern.slf4j.Slf4j;
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

    @Autowired
    private IClientService clientService;

    public ResponseEntity<ClientDto> getClientById(@NotNull final Long id) {
        final ClientDto clientDto = clientService.findById(id);
        return ResponseEntity.ok(clientDto);
    }

    public ResponseEntity<Page<ClientDto>> getClientByFilter(Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<ClientDto> clientDtoPage = clientService.findAllPaginatedByFilter(pageable, criteria);
        return ResponseEntity.ok(clientDtoPage);
    }

    public ResponseEntity<ClientDto> saveClient(@NotNull final ClientDto clientDto) {

        final ClientDto clientDtoSaved = clientService.saveClient(clientDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clientDtoSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(clientDtoSaved);

    }

    public ResponseEntity<ClientDto> updateClient(@NotNull final Long id, @NotNull final ClientDto clientDto) {

        final ClientDto clientDtoSaved = clientService.updateClient(id, clientDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clientDtoSaved.getId())
                .toUri();

        return ResponseEntity.ok().body(clientDtoSaved);

    }

    public ResponseEntity<?> deleteClient(@NotNull final Long id) {

        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();

    }

}
