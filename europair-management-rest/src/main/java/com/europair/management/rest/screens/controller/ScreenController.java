package com.europair.management.rest.screens.controller;

import com.europair.management.rest.screens.dto.ScreenDTO;
import com.europair.management.rest.screens.service.ScreenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/screens")
public class ScreenController {

  private final ScreenService screenService;

  @GetMapping("/")
  public ResponseEntity<List<ScreenDTO>> getAllScreens() {

    log.debug("Start - getAllScreens");

    final List<ScreenDTO> response = screenService.findAll();
    final ResponseEntity<List<ScreenDTO>> responseEntity = ResponseEntity.ok(response);

    log.debug("End - getAllScreens");
    return responseEntity;
  }


  @GetMapping("/{id}")
  public ResponseEntity<ScreenDTO> getScreenById(@PathVariable final Long id) {

    log.debug("Start - getScreenById");

    final ScreenDTO response = screenService.findById(id);
    final ResponseEntity<ScreenDTO> responseEntity = ResponseEntity.ok(response);

    log.debug("End - getScreenById");
    return responseEntity;
  }

}
