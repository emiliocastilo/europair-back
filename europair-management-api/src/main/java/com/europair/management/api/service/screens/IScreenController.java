package com.europair.management.api.service.screens;

import com.europair.management.api.dto.screens.dto.ScreenDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/screens")
public interface IScreenController {

  @GetMapping("")
  public ResponseEntity<Page<ScreenDTO>> getAllScreensPaginated(final Pageable pageable) ;

  @GetMapping("/{id}")
  public ResponseEntity<ScreenDTO> getScreenById(@PathVariable final Long id) ;

}
