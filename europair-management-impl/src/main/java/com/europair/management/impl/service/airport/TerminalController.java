package com.europair.management.impl.service.airport;


import com.europair.management.api.dto.airport.TerminalDto;
import com.europair.management.api.service.airport.ITerminalController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Map;

@RestController
@Slf4j
public class TerminalController implements ITerminalController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TerminalController.class);

    @Autowired
    private ITerminalService terminalService;

    @Override
    public ResponseEntity<TerminalDto> getTerminalById(@NotNull Long airportId, @NotNull Long id) {
        LOGGER.debug("[TerminalController] - Starting method [getTerminalById] with input: airportId={}, id={}", airportId, id);
        TerminalDto dto = terminalService.findById(airportId, id);
        LOGGER.debug("[TerminalController] - Ending method [getTerminalById] with return: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<TerminalDto>> getTerminalByFilter(@NotNull Long airportId, Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[TerminalController] - Starting method [getTerminalByFilter] with input: airportId={}, pageable={}, reqParam={}", airportId, pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<TerminalDto> dtoPage = terminalService.findAllPaginatedByFilter(airportId, pageable, criteria);
        LOGGER.debug("[TerminalController] - Ending method [getTerminalByFilter] with return: {}", dtoPage);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<TerminalDto> saveTerminal(@NotNull Long airportId, @NotNull TerminalDto terminalDto) {
        LOGGER.debug("[TerminalController] - Starting method [saveTerminal] with input: airportId={}, terminalDto={}", airportId, terminalDto);
        final TerminalDto dtoSaved = terminalService.saveTerminal(airportId, terminalDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(airportId, dtoSaved.getId())
                .toUri();

        LOGGER.debug("[TerminalController] - Ending method [saveTerminal] with return: {}", dtoSaved);
        return ResponseEntity.created(location).body(dtoSaved);
    }

    @Override
    public ResponseEntity<TerminalDto> updateTerminal(@NotNull Long airportId, @NotNull Long id, @NotNull TerminalDto terminalDto) {
        LOGGER.debug("[TerminalController] - Starting method [updateTerminal] with input: airportId={}, terminalDto={}", airportId, terminalDto);
        final TerminalDto dtoSaved = terminalService.updateTerminal(airportId, id, terminalDto);
        LOGGER.debug("[TerminalController] - Ending method [updateTerminal] with return: {}", dtoSaved);
        return ResponseEntity.ok(dtoSaved);
    }

    @Override
    public ResponseEntity<?> deleteTerminal(@NotNull Long airportId, @NotNull Long id) {
        LOGGER.debug("[TerminalController] - Starting method [deleteTerminal] with input: airportId={}, id={}", airportId, id);
        terminalService.deleteTerminal(airportId, id);
        LOGGER.debug("[TerminalController] - Ending method [deleteTerminal] with no return.");
        return ResponseEntity.noContent().build();
    }
}
