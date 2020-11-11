package com.europair.management.impl.service.conversions;

import com.europair.management.api.dto.conversions.ConversionDataDTO;
import com.europair.management.api.service.conversions.IConversionController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ConversionController implements IConversionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConversionController.class);

    private final ConversionService conversionService;

    public ResponseEntity<List<Double>> convertData(@NotNull @RequestBody final ConversionDataDTO conversionDataDTO) {
        LOGGER.debug("[ConversionController] - Starting method [convertData] with input: conversionDataDTO={}", conversionDataDTO);

        final List<Double> convertedData = conversionService.convertData(conversionDataDTO);

        LOGGER.debug("[ConversionController] - Ending method [convertData] with return: {}", convertedData);
        return ResponseEntity.ok().body(convertedData);
    }
}
