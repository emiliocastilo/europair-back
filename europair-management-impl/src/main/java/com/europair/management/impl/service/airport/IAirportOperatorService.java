package com.europair.management.impl.service.airport;

import com.europair.management.api.dto.operatorsairports.OperatorsAirportsDTO;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAirportOperatorService {

    Page<OperatorsAirportsDTO> findAllPaginatedByFilter(Long airportId, Pageable pageable, CoreCriteria criteria);

    OperatorsAirportsDTO findById(Long airportId, Long id);

    OperatorsAirportsDTO saveOperatorsAirports(Long airportId, OperatorsAirportsDTO operatorsAirportsDto);

    OperatorsAirportsDTO updateOperatorsAirports(Long airportId, Long id, OperatorsAirportsDTO operatorsAirportsDto);

    void deleteOperatorsAirports(Long airportId, Long id);
}
