package com.europair.management.api.dto.menu;

import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuOptionDto extends AuditModificationBaseDTO {

    private Long id;
    private String name;
    private String label;
    private Integer weight;
    private String icon;
    private String route;
    private List<MenuOptionDto> childs;

}
