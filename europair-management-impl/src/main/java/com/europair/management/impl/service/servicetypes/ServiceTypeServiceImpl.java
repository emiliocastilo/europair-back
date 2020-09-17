package com.europair.management.impl.service.servicetypes;

import com.europair.management.api.dto.servicetypes.ServiceTypeDTO;
import com.europair.management.impl.mappers.servicetypes.IServiceTypeMapper;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.servicetypes.entity.ServiceType;
import com.europair.management.rest.model.servicetypes.repository.ServiceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class ServiceTypeServiceImpl implements IServiceTypeService {

  @Autowired
  private ServiceTypeRepository serviceTypeRepository;

  @Override
  public Page<ServiceTypeDTO> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
    return serviceTypeRepository.findServiceTypeByCriteria(criteria, pageable)
      .map(IServiceTypeMapper.INSTANCE::toDto);
  }

  @Override
  public ServiceTypeDTO findById(Long id) {
    return IServiceTypeMapper.INSTANCE.toDto(serviceTypeRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ServiceType not found with id: " + id)));
  }

  @Override
  @Transactional(readOnly = false)
  public ServiceTypeDTO saveServiceType(ServiceTypeDTO serviceTypeDTO) {

    if (serviceTypeDTO.getId() != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New Service Type expected. Identifier %s got", serviceTypeDTO.getId()));
    }
    ServiceType serviceType = IServiceTypeMapper.INSTANCE.toEntity(serviceTypeDTO);
    serviceType = serviceTypeRepository.save(serviceType);

    return IServiceTypeMapper.INSTANCE.toDto(serviceType);

  }

  @Override
  @Transactional(readOnly = false)
  public ServiceTypeDTO updateServiceType(Long id, ServiceTypeDTO serviceTypeDTO) {
    ServiceType serviceType = serviceTypeRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ServiceType not found with id: " + id));

    IServiceTypeMapper.INSTANCE.updateFromDto(serviceTypeDTO, serviceType);
    serviceType = serviceTypeRepository.save(serviceType);

    return IServiceTypeMapper.INSTANCE.toDto(serviceType);
  }

  @Override
  @Transactional(readOnly = false)
  public void deleteServiceType(Long id) {
    if (!serviceTypeRepository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ServiceType not found with id: " + id);
    }
    serviceTypeRepository.deleteById(id);
  }
}
