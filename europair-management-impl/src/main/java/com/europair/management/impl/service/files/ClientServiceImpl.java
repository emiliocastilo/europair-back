package com.europair.management.impl.service.files;

import com.europair.management.api.dto.files.ClientDto;
import com.europair.management.impl.mappers.files.IClientMapper;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.files.entity.Client;
import com.europair.management.rest.model.files.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class ClientServiceImpl implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ClientDto findById(Long id) {
        return IClientMapper.INSTANCE.toDto(clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found with id: " + id)));
    }

    @Override
    public Page<ClientDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return clientRepository.findClientsByCriteria(criteria, pageable)
                .map(IClientMapper.INSTANCE::toDto);
    }

    @Override
    public ClientDto saveClient(final ClientDto clientDto) {

        if (clientDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New client expected. Identifier %s got", clientDto.getId()));
        }
        Client client = IClientMapper.INSTANCE.toEntity(clientDto);
        client = clientRepository.save(client);

        return IClientMapper.INSTANCE.toDto(client);
    }

    @Override
    public ClientDto updateClient(Long id, ClientDto clientDto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found with id: " + id));

        IClientMapper.INSTANCE.updateFromDto(clientDto, client);
        client = clientRepository.save(client);

        return IClientMapper.INSTANCE.toDto(client);
    }

    @Override
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found with id: " + id);
        }
        clientRepository.deleteById(id);
    }
}
