package com.europair.management.impl.service.services;

import com.europair.management.api.dto.services.ServiceDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IServiceService {

    Page<ServiceDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);

    ServiceDto findById(Long id);

    ServiceDto saveService(ServiceDto serviceDto);

    void updateService(Long id, ServiceDto serviceDto);

    void deleteService(Long id);

}
