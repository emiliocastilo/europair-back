package com.europair.management.impl.service.airport;

import com.europair.management.api.dto.airport.RunwayDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRunwayService {

    Page<RunwayDto> findAllPaginatedByFilter(Long airportId, Pageable pageable, CoreCriteria criteria);

    RunwayDto findById(Long airportId, Long id);

    RunwayDto saveRunway(Long airportId, RunwayDto runwayDto);

    RunwayDto updateRunway(Long airportId, Long id, RunwayDto runwayDto);

    void deleteRunway(Long airportId, Long id);
}
