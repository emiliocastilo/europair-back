package com.europair.management.rest.fleet.controller;

import com.europair.management.rest.fleet.service.AircraftService;
import com.europair.management.rest.model.fleet.dto.AircraftDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/aircrafts")
public class AircraftController {

    @Autowired
    private AircraftService aircraftService;

    @GetMapping
    public ResponseEntity<Page<AircraftDto>> getAllAircraftPaginated(final Pageable pageable) {
        final Page<AircraftDto> aircraftDtoPage = aircraftService.findAllPaginated(pageable);
        return ResponseEntity.ok(aircraftDtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AircraftDto> getAircraftById(@PathVariable final Long id) {
        final AircraftDto aircraftDto = aircraftService.findById(id);
        return ResponseEntity.ok(aircraftDto);
    }
}
