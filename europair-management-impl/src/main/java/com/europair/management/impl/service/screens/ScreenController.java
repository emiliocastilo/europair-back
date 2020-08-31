package com.europair.management.impl.service.screens;

import com.europair.management.api.dto.screens.ScreenDTO;
import com.europair.management.api.service.screens.IScreenController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final IScreenService IScreenService;

    public ResponseEntity<Page<ScreenDTO>> getAllScreensPaginated(final Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<ScreenDTO> pageScreensDTO = IScreenService.findAllPaginatedByFilter(pageable, criteria);
        return ResponseEntity.ok().body(pageScreensDTO);

    }

    public ResponseEntity<ScreenDTO> getScreenById(@PathVariable final Long id) {

        final ScreenDTO screenDTO = IScreenService.findById(id);
        return ResponseEntity.ok().body(screenDTO);
    }

}
