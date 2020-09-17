package com.europair.management.impl.service.files;

import com.europair.management.api.dto.files.ProviderDto;
import com.europair.management.impl.mappers.files.IProviderMapper;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.files.entity.Provider;
import com.europair.management.rest.model.files.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class ProviderServiceImpl implements IProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    @Override
    public ProviderDto findById(Long id) {
        return IProviderMapper.INSTANCE.toDto(providerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found with id: " + id)));
    }

    @Override
    public Page<ProviderDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return providerRepository.findProvidersByCriteria(criteria, pageable)
                .map(IProviderMapper.INSTANCE::toDto);
    }

    @Override
    public ProviderDto saveProvider(final ProviderDto providerDto) {

        if (providerDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New provider expected. Identifier %s got", providerDto.getId()));
        }
        Provider provider = IProviderMapper.INSTANCE.toEntity(providerDto);
        provider = providerRepository.save(provider);

        return IProviderMapper.INSTANCE.toDto(provider);
    }

    @Override
    public ProviderDto updateProvider(Long id, ProviderDto providerDto) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found with id: " + id));

        IProviderMapper.INSTANCE.updateFromDto(providerDto, provider);
        provider = providerRepository.save(provider);

        return IProviderMapper.INSTANCE.toDto(provider);
    }

    @Override
    public void deleteProvider(Long id) {
        if (!providerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found with id: " + id);
        }
        providerRepository.deleteById(id);
    }
}
