package com.europair.management.rest.model.masters.menu.dto;

import com.europair.management.rest.model.audit.dto.AuditModificationBaseDTO;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuOptionDto extends AuditModificationBaseDTO {

    private Long id;
    private String name;
    private String label;
    private String icon;
    private String route;
    private List<MenuOptionDto> childs;

}
