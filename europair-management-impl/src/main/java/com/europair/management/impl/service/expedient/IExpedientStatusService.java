package com.europair.management.impl.service.expedient;

import com.europair.management.api.dto.expedient.ExpedientStatusDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IExpedientStatusService {

    Page<ExpedientStatusDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);

    ExpedientStatusDto findById(Long id);

    ExpedientStatusDto saveExpedientStatus(ExpedientStatusDto expedientStatusDto);

    ExpedientStatusDto updateExpedientStatus(Long id, ExpedientStatusDto expedientStatusDto);

    void deleteExpedientStatus(Long id);
}
