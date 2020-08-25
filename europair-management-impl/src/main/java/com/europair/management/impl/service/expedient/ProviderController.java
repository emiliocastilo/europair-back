package com.europair.management.impl.service.expedient;

import com.europair.management.api.dto.expedient.ProviderDto;
import com.europair.management.api.service.expedient.IProviderController;
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
public class ProviderController implements IProviderController {

    @Autowired
    private IProviderService providerService;

    public ResponseEntity<ProviderDto> getProviderById(@NotNull final Long id) {
        final ProviderDto providerDto = providerService.findById(id);
        return ResponseEntity.ok(providerDto);
    }

    public ResponseEntity<Page<ProviderDto>> getProviderByFilter(Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<ProviderDto> providerDtoPage = providerService.findAllPaginatedByFilter(pageable, criteria);
        return ResponseEntity.ok(providerDtoPage);
    }

    public ResponseEntity<ProviderDto> saveProvider(@NotNull final ProviderDto providerDto) {

        final ProviderDto providerDtoSaved = providerService.saveProvider(providerDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(providerDtoSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(providerDtoSaved);

    }

    public ResponseEntity<ProviderDto> updateProvider(@NotNull final Long id, @NotNull final ProviderDto providerDto) {

        final ProviderDto providerDtoSaved = providerService.updateProvider(id, providerDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(providerDtoSaved.getId())
                .toUri();

        return ResponseEntity.ok().body(providerDtoSaved);

    }

    public ResponseEntity<?> deleteProvider(@NotNull final Long id) {

        providerService.deleteProvider(id);
        return ResponseEntity.noContent().build();

    }

}
