package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftDto;
import com.europair.management.api.dto.fleet.AircraftFilterDto;
import com.europair.management.api.dto.fleet.AircraftSearchResultDataDto;
import com.europair.management.rest.model.fleet.entity.Aircraft;

import java.util.List;

public interface IAircraftSearchService {

    List<AircraftSearchResultDataDto> searchAircraft(AircraftFilterDto filterDto);

    List<AircraftDto> test(AircraftFilterDto filterDto);
}
