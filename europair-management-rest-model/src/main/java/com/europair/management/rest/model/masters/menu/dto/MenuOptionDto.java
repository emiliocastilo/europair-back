package com.europair.management.rest.model.masters.menu.dto;

import lombok.Builder;
import lombok.Value;

import javax.persistence.*;
import java.util.List;

@Value
@Builder
public class MenuOptionDto {

    private Long id;
    private String name;
    private String label;
    private String icon;
    private String route;
    private List<MenuOptionDto> childs;

}
