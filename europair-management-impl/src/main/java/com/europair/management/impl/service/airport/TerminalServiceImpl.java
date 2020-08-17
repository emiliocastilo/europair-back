package com.europair.management.impl.service.airport;

import com.europair.management.api.dto.airport.TerminalDto;
import com.europair.management.impl.common.exception.InvalidArgumentException;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.airport.ITerminalMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.airport.entity.Terminal;
import com.europair.management.rest.model.airport.repository.AirportRepository;
import com.europair.management.rest.model.airport.repository.TerminalRepository;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TerminalServiceImpl implements ITerminalService {

    private final String AIRPORT_ID_FILTER = "airport.id";

    @Autowired
    private TerminalRepository terminalRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Override
    public Page<TerminalDto> findAllPaginatedByFilter(final Long airportId, Pageable pageable, CoreCriteria criteria) {
        checkIfAirportExists(airportId);
        Utils.addCriteriaIfNotExists(criteria, AIRPORT_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(airportId));

        return terminalRepository.findTerminalByCriteria(criteria, pageable)
                .map(ITerminalMapper.INSTANCE::toDto);
    }

    @Override
    public TerminalDto findById(final Long airportId, Long id) {
        checkIfAirportExists(airportId);
        return ITerminalMapper.INSTANCE.toDto(terminalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Terminal not found with id: " + id)));
    }

    @Override
    public TerminalDto saveTerminal(final Long airportId, TerminalDto terminalDto) {
        checkIfAirportExists(airportId);
        if (terminalDto.getId() != null) {
            throw new InvalidArgumentException(String.format("New Terminal expected. Identifier %s got", terminalDto.getId()));
        }

        Terminal terminal = ITerminalMapper.INSTANCE.toEntity(terminalDto);
        terminal = terminalRepository.save(terminal);

        return ITerminalMapper.INSTANCE.toDto(terminal);
    }

    @Override
    public TerminalDto updateTerminal(final Long airportId, Long id, TerminalDto terminalDto) {
        checkIfAirportExists(airportId);
        Terminal terminal = terminalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Terminal not found with id: " + id));
        ITerminalMapper.INSTANCE.updateFromDto(terminalDto, terminal);
        terminal = terminalRepository.save(terminal);

        return ITerminalMapper.INSTANCE.toDto(terminal);
    }

    @Override
    public void deleteTerminal(final Long airportId, Long id) {
        checkIfAirportExists(airportId);
        if (!terminalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Terminal not found with id: " + id);
        }
        terminalRepository.deleteById(id);
    }

    private void checkIfAirportExists(final Long airportId) {
        if (!airportRepository.existsById(airportId)) {
            throw new ResourceNotFoundException("Airport not found with id: " + airportId);
        }
    }

}
