package com.europair.management.impl.common.service;

import com.europair.management.api.enums.ContributionStatesEnum;
import com.europair.management.api.enums.FileStatesEnum;
import com.europair.management.api.enums.RouteStatesEnum;
import com.europair.management.api.service.common.IEnumValuesController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Slf4j
public class EnumValuesController implements IEnumValuesController {

    @Override
    public ResponseEntity<List<String>> getFileStatesValues() {
        List<String> res = Stream.of(FileStatesEnum.values()).map(FileStatesEnum::name).collect(Collectors.toList());
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<List<String>> getRouteStatesValues() {
        List<String> res = Stream.of(RouteStatesEnum.values()).map(RouteStatesEnum::name).collect(Collectors.toList());
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<List<String>> getContributionStatesValues() {
        List<String> res = Stream.of(ContributionStatesEnum.values()).map(ContributionStatesEnum::name).collect(Collectors.toList());
        return ResponseEntity.ok(res);
    }
}
