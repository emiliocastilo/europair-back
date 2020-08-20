package com.europair.management.impl.service.airport;

import com.europair.management.api.dto.airport.TerminalDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITerminalService {

    Page<TerminalDto> findAllPaginatedByFilter(Long airportId, Pageable pageable, CoreCriteria criteria);

    TerminalDto findById(Long airportId, Long id);

    TerminalDto saveTerminal(Long airportId, TerminalDto terminalDto);

    TerminalDto updateTerminal(Long airportId, Long id, TerminalDto terminalDto);

    void deleteTerminal(Long airportId, Long id);
}
