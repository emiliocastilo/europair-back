package com.europair.management.impl.service.services;

import com.europair.management.api.dto.services.ServiceDto;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.mappers.servicetypes.IServiceTypeMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.services.entity.Service;
import com.europair.management.rest.model.services.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Service
@Transactional(readOnly = true)
public class ServiceServiceImpl implements IServiceService {

  @Autowired
  private ServiceRepository serviceTypeRepository;

  @Override
  public Page<ServiceDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
    return serviceTypeRepository.findServiceByCriteria(criteria, pageable)
      .map(IServiceTypeMapper.INSTANCE::toDto);
  }

  @Override
  public ServiceDto findById(Long id) {
    return IServiceTypeMapper.INSTANCE.toDto(serviceTypeRepository.findById(id)
      .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.SERVICE_NOT_FOUND, String.valueOf(id))));
  }

  @Override
  @Transactional(readOnly = false)
  public ServiceDto saveService(ServiceDto serviceDto) {
    if (serviceDto.getId() != null) {
      throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.SERVICE_NEW_WITH_ID, String.valueOf(serviceDto.getId()));
    }
    Service service = IServiceTypeMapper.INSTANCE.toEntity(serviceDto);
    service = serviceTypeRepository.save(service);

    return IServiceTypeMapper.INSTANCE.toDto(service);

  }

  @Override
  @Transactional(readOnly = false)
  public void updateService(Long id, ServiceDto serviceDto) {
    Service service = serviceTypeRepository.findById(id)
      .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.SERVICE_NOT_FOUND, String.valueOf(id)));

    serviceDto.setId(id);
    serviceDto.setId(id);serviceDto.setId(id);IServiceTypeMapper.INSTANCE.updateFromDto(serviceDto, service);
    service = serviceTypeRepository.save(service);
    // ToDo Log msg update ok
  }

  @Override
  @Transactional(readOnly = false)
  public void deleteService(Long id) {
    if (!serviceTypeRepository.existsById(id)) {
      throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.SERVICE_NOT_FOUND, String.valueOf(id));
    }
    serviceTypeRepository.deleteById(id);
  }
}
