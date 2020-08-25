package com.europair.management.impl.service.expedient;

import com.europair.management.api.dto.expedient.ProviderDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProviderService {

    Page<ProviderDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);

    ProviderDto findById(Long id);

    ProviderDto saveProvider(ProviderDto providerDto);

    ProviderDto updateProvider(Long id, ProviderDto providerDto);

    void deleteProvider(Long id);
}
