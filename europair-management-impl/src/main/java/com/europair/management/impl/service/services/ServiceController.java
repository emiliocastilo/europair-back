package com.europair.management.impl.service.services;

import com.europair.management.api.dto.services.ServiceDto;
import com.europair.management.api.service.services.IServiceController;
import com.europair.management.impl.service.screens.ScreenController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceController.class);

    @Autowired
    private IServiceService serviceService;

    @Override
    public ResponseEntity<Page<ServiceDto>> getServiceByFilter(Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[ServiceController] - Starting method [getServiceByFilter] with input: pageable={}, reqParam={}", pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<ServiceDto> serviceDTOPage = serviceService.findAllPaginatedByFilter(pageable, criteria);
        LOGGER.debug("[ServiceController] - Ending method [getServiceByFilter] with return: {}", serviceDTOPage);
        return ResponseEntity.ok(serviceDTOPage);
    }

    @Override
    public ResponseEntity<ServiceDto> getServiceById(@NotNull Long id) {
        LOGGER.debug("[ServiceController] - Starting method [getServiceById] with input: id={}", id);
        final ServiceDto serviceDto = serviceService.findById(id);
        LOGGER.debug("[ServiceController] - Ending method [getServiceById] with return: {}", serviceDto);
        return ResponseEntity.ok(serviceDto);
    }

    @Override
    public ResponseEntity<ServiceDto> saveService(@NotNull ServiceDto serviceDto) {
        LOGGER.debug("[ServiceController] - Starting method [saveService] with input: serviceDto={}", serviceDto);
        final ServiceDto serviceDtoSaved = serviceService.saveService(serviceDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(serviceDtoSaved.getId())
                .toUri();
        LOGGER.debug("[ServiceController] - Ending method [saveService] with return: {}", serviceDtoSaved);
        return ResponseEntity.created(location).body(serviceDtoSaved);
    }

    @Override
    public ResponseEntity<?> updateService(@NotNull Long id, @NotNull ServiceDto serviceDto) {
        LOGGER.debug("[ServiceController] - Starting method [updateService] with input: id={}, serviceDto={}", id, serviceDto);
        serviceService.updateService(id, serviceDto);
        LOGGER.debug("[ServiceController] - Ending method [updateService] with no return.");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> deleteService(@NotNull Long id) {
        LOGGER.debug("[ServiceController] - Starting method [deleteService] with input: id={}", id);
        serviceService.deleteService(id);
        LOGGER.debug("[ServiceController] - Ending method [deleteService] with no return.");
        return ResponseEntity.noContent().build();
    }
}
