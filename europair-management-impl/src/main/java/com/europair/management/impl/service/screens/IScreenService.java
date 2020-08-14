package com.europair.management.impl.service.screens;

import com.europair.management.api.dto.screens.ScreenDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IScreenService {

  Page<ScreenDTO> findAllPaginated(Pageable pageable);

  ScreenDTO findById(Long id);

}