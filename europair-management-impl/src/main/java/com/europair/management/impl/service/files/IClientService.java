package com.europair.management.impl.service.files;

import com.europair.management.api.dto.files.ClientDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClientService {

    Page<ClientDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);

    ClientDto findById(Long id);

    ClientDto saveClient(ClientDto clientDto);

    ClientDto updateClient(Long id, ClientDto clientDto);

    void deleteClient(Long id);
}
