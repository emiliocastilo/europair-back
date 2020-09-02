package com.europair.management.api.dto.conversions;

import com.europair.management.api.enums.UTCEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransformUTCTimeFromToDTO {

    @JsonProperty("fromUTCIndicator")
    @NotEmpty
    private UTCEnum fromUTCIndicator;

    @JsonProperty("time")
    @NotEmpty
    private String time;

    @JsonProperty("toUTCIndicator")
    @NotEmpty
    private UTCEnum toUTCIndicator;


}
