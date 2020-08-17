package com.europair.management.impl.service.airport;

import com.europair.management.api.dto.airport.AirportDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAirportService {

    Page<AirportDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);

    AirportDto findById(Long id);

    AirportDto saveAirport(AirportDto airportDto);

    AirportDto updateAirport(Long id, AirportDto airportDto);

    void deleteAirport(Long id);
}
