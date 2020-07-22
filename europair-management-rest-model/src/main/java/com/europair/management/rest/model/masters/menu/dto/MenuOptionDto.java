package com.europair.management.rest.model.masters.menu.dto;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuOptionDto {

    private Long id;
    private String name;
    private String label;
    private String icon;
    private String route;
    private List<MenuOptionDto> childs;

}
