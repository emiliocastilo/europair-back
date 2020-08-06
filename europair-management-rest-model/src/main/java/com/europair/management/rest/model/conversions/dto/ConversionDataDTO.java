package com.europair.management.rest.model.conversions.dto;

import com.europair.management.rest.model.audit.dto.AuditModificationBaseDTO;
import com.europair.management.rest.model.conversions.common.Unit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Vectorized conversion data. Only srcUnit or dstUnit must be provided.")
public class ConversionDataDTO {

    @Data
    @Schema(description = "Conversion data. Only srcUnit or dstUnit must be provided.")
    public static class ConversionTuple {
        @Schema(description = "Source unit")
        private Unit srcUnit;
        @Schema(description = "Destination unit")
        private Unit dstUnit;
        @NotBlank(message = "Value is mandatory")
        @Schema(description = "Value to be converted")
        private double value;
    }

    @Schema(description = "Source unit")
    private Unit srcUnit;

    @Schema(description = "Destination unit")
    private Unit dstUnit;

    @NotEmpty(message = "Data to convert is mandatory")
    @Schema(description = "Data to be converted")
    List<ConversionTuple> dataToConvert;
}
