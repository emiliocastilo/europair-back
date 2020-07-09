package com.europair.management.rest.screens.service;

import com.europair.management.rest.model.screens.dto.ScreenDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScreenService {

  Page<ScreenDTO> findAllPaginated(Pageable pageable);

  ScreenDTO findById(Long id);

}
