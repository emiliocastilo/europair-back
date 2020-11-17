package com.europair.management.impl.service.screens;

import com.europair.management.api.dto.screens.ScreenDTO;
import com.europair.management.api.service.screens.IScreenController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ScreenController implements IScreenController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScreenController.class);

    private final IScreenService IScreenService;

    public ResponseEntity<Page<ScreenDTO>> getAllScreensPaginated(final Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[ScreenController] - Starting method [getAllScreensPaginated] with input: pageable={}, reqParam={}", pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<ScreenDTO> pageScreensDTO = IScreenService.findAllPaginatedByFilter(pageable, criteria);
        LOGGER.debug("[ScreenController] - Ending method [getAllScreensPaginated] with return: {}", pageScreensDTO);
        return ResponseEntity.ok().body(pageScreensDTO);
    }

    public ResponseEntity<ScreenDTO> getScreenById(@PathVariable final Long id) {
        LOGGER.debug("[ScreenController] - Starting method [getScreenById] with input: id={}", id);
        final ScreenDTO screenDTO = IScreenService.findById(id);
        LOGGER.debug("[ScreenController] - Ending method [getScreenById] with return: {}", screenDTO);
        return ResponseEntity.ok().body(screenDTO);
    }

}
