package com.europair.management.impl.service.files;

import com.europair.management.api.dto.files.ProviderDto;
import com.europair.management.api.service.files.IProviderController;
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
public class ProviderController implements IProviderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderController.class);

    @Autowired
    private IProviderService providerService;

    public ResponseEntity<ProviderDto> getProviderById(@NotNull final Long id) {
        LOGGER.debug("[ProviderController] - Starting method [getProviderById] with input: id={}", id);
        final ProviderDto providerDto = providerService.findById(id);
        LOGGER.debug("[ProviderController] - Ending method [getProviderById] with return: {}", providerDto);
        return ResponseEntity.ok(providerDto);
    }

    public ResponseEntity<Page<ProviderDto>> getProviderByFilter(Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[ProviderController] - Starting method [getProviderByFilter] with input: pageable={}, reqParam={}", pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<ProviderDto> providerDtoPage = providerService.findAllPaginatedByFilter(pageable, criteria);
        LOGGER.debug("[ProviderController] - Ending method [getProviderByFilter] with return: {}", providerDtoPage);
        return ResponseEntity.ok(providerDtoPage);
    }

    public ResponseEntity<ProviderDto> saveProvider(@NotNull final ProviderDto providerDto) {
        LOGGER.debug("[ProviderController] - Starting method [saveProvider] with input: providerDto={}", providerDto);

        final ProviderDto providerDtoSaved = providerService.saveProvider(providerDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(providerDtoSaved.getId())
                .toUri();

        LOGGER.debug("[ProviderController] - Ending method [saveProvider] with return: {}", providerDtoSaved);
        return ResponseEntity.created(location).body(providerDtoSaved);

    }

    public ResponseEntity<ProviderDto> updateProvider(@NotNull final Long id, @NotNull final ProviderDto providerDto) {
        LOGGER.debug("[ProviderController] - Starting method [updateProvider] with input: id={}, providerDto={}", id, providerDto);
        final ProviderDto providerDtoSaved = providerService.updateProvider(id, providerDto);
        LOGGER.debug("[ProviderController] - Ending method [updateProvider] with return: {}", providerDtoSaved);
        return ResponseEntity.ok().body(providerDtoSaved);

    }

    public ResponseEntity<?> deleteProvider(@NotNull final Long id) {
        LOGGER.debug("[ProviderController] - Starting method [deleteProvider] with input: id={}", id);
        providerService.deleteProvider(id);
        LOGGER.debug("[ProviderController] - Ending method [deleteProvider] with no return.");
        return ResponseEntity.noContent().build();
    }

}
