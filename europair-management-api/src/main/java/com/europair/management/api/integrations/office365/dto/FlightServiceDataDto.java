package com.europair.management.api.integrations.office365.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class FlightServiceDataDto implements Serializable {

    private String code;
    private String description;
    private Integer quantity;
    private String provider; // Code + Name
    private BigDecimal purchaseAmount;
    private BigDecimal saleAmount;
    private String comments;
    private String status; // ToDo: estado de los serv adicionales está pendiente en la entidad (solo los pendientes o confirmados, rechazados no se mandan)

}
