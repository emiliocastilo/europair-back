package com.europair.management.impl.service.servicetypes;

import com.europair.management.api.dto.files.FileDTO;
import com.europair.management.api.dto.servicetypes.ServiceTypeDTO;
import com.europair.management.api.service.servicetypes.IServiceTypeController;
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
public class ServiceTypeController implements IServiceTypeController {

  @Autowired
  private  IServiceTypeService serviceTypeService;

  @Override
  public ResponseEntity<Page<ServiceTypeDTO>> getServiceTypeByFilter(Pageable pageable, Map<String, String> reqParam) {
    CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
    final Page<ServiceTypeDTO> serviceTypeDTOPage = serviceTypeService.findAllPaginatedByFilter(pageable, criteria);
    return ResponseEntity.ok(serviceTypeDTOPage);
  }

  @Override
  public ResponseEntity<ServiceTypeDTO> getServiceTypeById(@NotNull Long id) {
    final ServiceTypeDTO serviceTypeDTO = serviceTypeService.findById(id);
    return ResponseEntity.ok(serviceTypeDTO);
  }

  @Override
  public ResponseEntity<ServiceTypeDTO> saveServiceType(@NotNull ServiceTypeDTO serviceTypeDTO) {

    final ServiceTypeDTO serviceTypeDTOSaved = serviceTypeService.saveServiceType(serviceTypeDTO);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(serviceTypeDTOSaved.getId())
      .toUri();

    return ResponseEntity.created(location).body(serviceTypeDTOSaved);

  }

  @Override
  public ResponseEntity<ServiceTypeDTO> updateServiceType(@NotNull Long id, @NotNull ServiceTypeDTO serviceTypeDTO) {

    final ServiceTypeDTO serviceTypeDTOUpdated = serviceTypeService.updateServiceType(id, serviceTypeDTO);
    return ResponseEntity.ok().body(serviceTypeDTOUpdated);

  }

  @Override
  public ResponseEntity<?> deleteServiceType(@NotNull Long id) {

    serviceTypeService.deleteServiceType(id);
    return ResponseEntity.noContent().build();
  }
}
