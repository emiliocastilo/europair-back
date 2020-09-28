package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftFilterDto;
import com.europair.management.api.dto.fleet.AircraftSearchResultDataDto;

import java.util.List;

public interface IAircraftSearchService {

    List<AircraftSearchResultDataDto> searchAircraft(AircraftFilterDto filterDto);

}
