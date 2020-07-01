package com.europair.management.rest.screens.service.impl;

import com.europair.management.rest.screens.dto.ScreenDTO;
import com.europair.management.rest.screens.entity.Screen;
import com.europair.management.rest.screens.mapper.ScreenMapper;
import com.europair.management.rest.screens.repository.ScrrenRepository;
import com.europair.management.rest.screens.service.ScreenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScreenServiceImpl implements ScreenService {

  private final ScrrenRepository screenRepository;

  @Override
  public List<ScreenDTO> findAll() {
    return ScreenMapper.INSTANCE.toListDtos(screenRepository.findAll());
  }

  @Override
  public ScreenDTO findById(Long id) {
    return ScreenMapper.INSTANCE.toDto((Screen) screenRepository.findById(id).get());
  }
}
