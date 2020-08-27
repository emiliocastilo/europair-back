package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftDto;
import com.europair.management.api.dto.fleet.AircraftFilterDto;

import java.util.List;

public interface IAircraftSearchService {

    List<AircraftDto> searchAircraft(AircraftFilterDto filterDto);
}
