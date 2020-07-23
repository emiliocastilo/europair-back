package com.europair.management.rest.screens.service.impl;

import com.europair.management.rest.common.exception.ResourceNotFoundException;
import com.europair.management.rest.model.screens.dto.ScreenDTO;
import com.europair.management.rest.model.screens.mapper.ScreenMapper;
import com.europair.management.rest.screens.repository.ScreenRepository;
import com.europair.management.rest.screens.service.ScreenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScreenServiceImpl implements ScreenService {

  private final ScreenRepository screenRepository;

  @Override
  public Page<ScreenDTO> findAllPaginated(Pageable pageable) {
    return screenRepository.findAll(pageable).map(screen -> ScreenMapper.INSTANCE.toDto(screen));
  }

  @Override
  public ScreenDTO findById(Long id) throws ResourceNotFoundException {
    return ScreenMapper.INSTANCE.toDto(screenRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Screen not found on id: " + id)));
  }
}
