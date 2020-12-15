package com.europair.management.impl.service.files;

import com.europair.management.api.dto.files.ProviderDto;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.mappers.files.IProviderMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.files.entity.Provider;
import com.europair.management.rest.model.files.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProviderServiceImpl implements IProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    @Override
    public ProviderDto findById(Long id) {
        return IProviderMapper.INSTANCE.toDto(providerRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.PROVIDER_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    public Page<ProviderDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return providerRepository.findProvidersByCriteria(criteria, pageable)
                .map(IProviderMapper.INSTANCE::toDto);
    }

    @Override
    public ProviderDto saveProvider(final ProviderDto providerDto) {

        if (providerDto.getId() != null) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.PROVIDER_NEW_WITH_ID, String.valueOf(providerDto.getId()));
        }
        Provider provider = IProviderMapper.INSTANCE.toEntity(providerDto);
        provider = providerRepository.save(provider);

        return IProviderMapper.INSTANCE.toDto(provider);
    }

    @Override
    public ProviderDto updateProvider(Long id, ProviderDto providerDto) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.PROVIDER_NOT_FOUND, String.valueOf(id)));

        IProviderMapper.INSTANCE.updateFromDto(providerDto, provider);
        provider = providerRepository.save(provider);

        return IProviderMapper.INSTANCE.toDto(provider);
    }

    @Override
    public void deleteProvider(Long id) {
        if (!providerRepository.existsById(id)) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.PROVIDER_NOT_FOUND, String.valueOf(id));
        }
        providerRepository.deleteById(id);
    }
}
