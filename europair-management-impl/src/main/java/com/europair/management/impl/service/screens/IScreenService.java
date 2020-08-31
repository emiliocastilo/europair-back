package com.europair.management.impl.service.screens;

import com.europair.management.api.dto.screens.ScreenDTO;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IScreenService {

    Page<ScreenDTO> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);

    ScreenDTO findById(Long id);

}
