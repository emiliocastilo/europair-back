package com.europair.management.impl.service.expedient;

import com.europair.management.api.dto.expedient.ExpedientStatusDto;
import com.europair.management.api.service.expedient.IExpedientStatusController;
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
public class ExpedientStatusController implements IExpedientStatusController {

    @Autowired
    private IExpedientStatusService expedientStatusService;

    public ResponseEntity<ExpedientStatusDto> getExpedientStatusById(@NotNull final Long id) {
        final ExpedientStatusDto expedientStatusDto = expedientStatusService.findById(id);
        return ResponseEntity.ok(expedientStatusDto);
    }

    public ResponseEntity<Page<ExpedientStatusDto>> getExpedientStatusByFilter(Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<ExpedientStatusDto> expedientStatusDtoPage = expedientStatusService.findAllPaginatedByFilter(pageable, criteria);
        return ResponseEntity.ok(expedientStatusDtoPage);
    }

    public ResponseEntity<ExpedientStatusDto> saveExpedientStatus(@NotNull final ExpedientStatusDto expedientStatusDto) {

        final ExpedientStatusDto expedientStatusDtoSaved = expedientStatusService.saveExpedientStatus(expedientStatusDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(expedientStatusDtoSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(expedientStatusDtoSaved);

    }

    public ResponseEntity<ExpedientStatusDto> updateExpedientStatus(@NotNull final Long id, @NotNull final ExpedientStatusDto expedientStatusDto) {

        final ExpedientStatusDto expedientStatusDtoSaved = expedientStatusService.updateExpedientStatus(id, expedientStatusDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(expedientStatusDtoSaved.getId())
                .toUri();

        return ResponseEntity.ok().body(expedientStatusDtoSaved);

    }

    public ResponseEntity<?> deleteExpedientStatus(@NotNull final Long id) {

        expedientStatusService.deleteExpedientStatus(id);
        return ResponseEntity.noContent().build();

    }

}
