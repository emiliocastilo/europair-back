package com.europair.management.impl.service.servicetypes;

import com.europair.management.api.dto.servicetypes.ServiceTypeDTO;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IServiceTypeService {

  Page<ServiceTypeDTO> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);

  ServiceTypeDTO findById(Long id);

  ServiceTypeDTO saveServiceType(ServiceTypeDTO serviceTypeDTO);

  ServiceTypeDTO updateServiceType(Long id, ServiceTypeDTO serviceTypeDTO);

  void deleteServiceType(Long id);

}
