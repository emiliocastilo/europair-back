package com.europair.management.impl.service.services;

import com.europair.management.api.dto.services.ServiceDto;
import com.europair.management.api.service.services.IServiceController;
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
public class ServiceController implements IServiceController {

  @Autowired
  private IServiceService serviceService;

  @Override
  public ResponseEntity<Page<ServiceDto>> getServiceByFilter(Pageable pageable, Map<String, String> reqParam) {
    CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
    final Page<ServiceDto> serviceDTOPage = serviceService.findAllPaginatedByFilter(pageable, criteria);
    return ResponseEntity.ok(serviceDTOPage);
  }

  @Override
  public ResponseEntity<ServiceDto> getServiceById(@NotNull Long id) {
      final ServiceDto serviceDto = serviceService.findById(id);
      return ResponseEntity.ok(serviceDto);
  }

  @Override
  public ResponseEntity<ServiceDto> saveService(@NotNull ServiceDto serviceDto) {
    final ServiceDto serviceDtoSaved = serviceService.saveService(serviceDto);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(serviceDtoSaved.getId())
      .toUri();
    return ResponseEntity.created(location).body(serviceDtoSaved);
  }

  @Override
  public ResponseEntity<?> updateService(@NotNull Long id, @NotNull ServiceDto serviceDto) {
      serviceService.updateService(id, serviceDto);
      return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<?> deleteService(@NotNull Long id) {
    serviceService.deleteService(id);
    return ResponseEntity.noContent().build();
  }
}
