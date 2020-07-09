package com.europair.management.rest.screens.controller;

import com.europair.management.rest.model.screens.dto.ScreenDTO;
import com.europair.management.rest.screens.service.ScreenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/screens")
public class ScreenController {

  private final ScreenService screenService;

  @GetMapping("")
  public ResponseEntity<Page<ScreenDTO>> getAllScreensPaginated(final Pageable pageable) {

    final Page<ScreenDTO> pageScreensDTO = screenService.findAllPaginated(pageable);
    return ResponseEntity.ok().body(pageScreensDTO);

  }

  @GetMapping("/{id}")
  public ResponseEntity<ScreenDTO> getScreenById(@PathVariable final Long id) {

    final ScreenDTO screenDTO = screenService.findById(id);
    return ResponseEntity.ok().body(screenDTO);
  }

}
