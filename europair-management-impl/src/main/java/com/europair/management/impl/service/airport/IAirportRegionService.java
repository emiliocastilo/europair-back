package com.europair.management.impl.service.airport;

import com.europair.management.api.dto.regions.RegionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAirportRegionService {

    Page<RegionDTO> findAllPaginatedByFilter(Long airportId, Pageable pageable);

    RegionDTO findById(Long airportId, Long id);

    RegionDTO saveRegionAirport(Long airportId, RegionDTO regionDto);

    void deleteRegionAirport(Long airportId, Long id);
}
