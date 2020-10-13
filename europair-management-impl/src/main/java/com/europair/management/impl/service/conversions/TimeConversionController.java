package com.europair.management.impl.service.conversions;

import com.europair.management.api.dto.conversions.TransformUTCTimeFromToDTO;
import com.europair.management.api.enums.UTCEnum;
import com.europair.management.api.service.conversions.ITimeConversionController;
import com.europair.management.impl.util.Utils;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TimeConversionController implements ITimeConversionController {


    @Override
    public ResponseEntity<String> transformTimeFromUTCIndicatorToUTCIndicator(TransformUTCTimeFromToDTO transformUTCTimeFromToDTO) {

        String res = null;

        LocalDateTime utcTime = null;
        utcTime = Utils.TimeConverter.getLocalTimeInOtherUTC(transformUTCTimeFromToDTO.getFromUTCIndicator(),
                transformUTCTimeFromToDTO.getTime(),
                transformUTCTimeFromToDTO.getToUTCIndicator());
        res = utcTime.toString();

        return ResponseEntity.ok().body(res);
    }

    @Override
    public ResponseEntity<String> getUTCEnum() {
        return ResponseEntity.ok().body(UTCEnum.getAllValues());
    }

}
