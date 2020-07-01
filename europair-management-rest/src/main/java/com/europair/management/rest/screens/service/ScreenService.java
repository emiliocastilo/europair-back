package com.europair.management.rest.screens.service;

import com.europair.management.rest.screens.dto.ScreenDTO;
import com.europair.management.rest.screens.entity.Screen;

import java.util.List;
import java.util.Optional;

public interface ScreenService {

  List<ScreenDTO> findAll();

  ScreenDTO findById(Long id);

}
