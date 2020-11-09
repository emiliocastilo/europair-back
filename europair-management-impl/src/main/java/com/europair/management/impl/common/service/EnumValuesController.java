package com.europair.management.impl.common.service;

import com.europair.management.api.enums.ContributionStatesEnum;
import com.europair.management.api.enums.FileStatesEnum;
import com.europair.management.api.enums.RouteStatesEnum;
import com.europair.management.api.service.common.IEnumValuesController;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Slf4j
public class EnumValuesController implements IEnumValuesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnumValuesController.class);

    @Override
    public ResponseEntity<List<String>> getFileStatesValues() {
        LOGGER.debug("[EnumValuesController] - Starting method [getFileStatesValues] with no input");
        List<String> res = Stream.of(FileStatesEnum.values()).map(FileStatesEnum::name).collect(Collectors.toList());
        LOGGER.debug("[EnumValuesController] - Ending method [getFileStatesValues] with return: {}", res);
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<List<String>> getRouteStatesValues() {
        LOGGER.debug("[EnumValuesController] - Starting method [getRouteStatesValues] with no input");
        List<String> res = Stream.of(RouteStatesEnum.values()).map(RouteStatesEnum::name).collect(Collectors.toList());
        LOGGER.debug("[EnumValuesController] - Ending method [getRouteStatesValues] with return: {}", res);
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<List<String>> getContributionStatesValues() {
        LOGGER.debug("[EnumValuesController] - Starting method [getContributionStatesValues] with no input");
        List<String> res = Stream.of(ContributionStatesEnum.values()).map(ContributionStatesEnum::name).collect(Collectors.toList());
        LOGGER.debug("[EnumValuesController] - Ending method [getContributionStatesValues] with return: {}", res);
        return ResponseEntity.ok(res);
    }
}
