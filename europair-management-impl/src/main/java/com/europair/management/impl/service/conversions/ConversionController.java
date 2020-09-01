package com.europair.management.impl.service.conversions;

import com.europair.management.api.dto.conversions.ConversionDataDTO;
import com.europair.management.api.service.conversions.IConversionController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ConversionController implements IConversionController {

    private final ConversionService conversionService;

    public ResponseEntity<List<Double>> convertData(@NotNull @RequestBody final ConversionDataDTO conversionDataDTO) {

        final List<Double> convertedData = conversionService.convertData(conversionDataDTO);

        return ResponseEntity.ok().body(convertedData);
    }
}
