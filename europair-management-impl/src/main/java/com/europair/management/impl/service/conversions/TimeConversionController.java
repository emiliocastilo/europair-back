package com.europair.management.impl.service.conversions;

import com.europair.management.api.dto.conversions.TransformUTCTimeFromToDTO;
import com.europair.management.api.enums.UTCEnum;
import com.europair.management.api.service.conversions.ITimeConversionController;
import com.europair.management.impl.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TimeConversionController implements ITimeConversionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeConversionController.class);

    @Override
    public ResponseEntity<String> transformTimeFromUTCIndicatorToUTCIndicator(TransformUTCTimeFromToDTO transformUTCTimeFromToDTO) {
        LOGGER.debug("[TimeConversionController] - Starting method [transformTimeFromUTCIndicatorToUTCIndicator] with input: transformUTCTimeFromToDTO={}", transformUTCTimeFromToDTO);

        String res = null;

        LocalDateTime utcTime = null;
        utcTime = Utils.TimeConverter.getLocalTimeInOtherUTC(transformUTCTimeFromToDTO.getFromUTCIndicator(),
                transformUTCTimeFromToDTO.getTime(),
                transformUTCTimeFromToDTO.getToUTCIndicator());
        res = utcTime.toString();

        LOGGER.debug("[TimeConversionController] - Ending method [transformTimeFromUTCIndicatorToUTCIndicator] with return: {}", res);
        return ResponseEntity.ok().body(res);
    }

    @Override
    public ResponseEntity<String> getUTCEnum() {
        LOGGER.debug("[TimeConversionController] - Starting method [getUTCEnum] with no input and return: {}", UTCEnum.getAllValues());
        return ResponseEntity.ok().body(UTCEnum.getAllValues());
    }

}
