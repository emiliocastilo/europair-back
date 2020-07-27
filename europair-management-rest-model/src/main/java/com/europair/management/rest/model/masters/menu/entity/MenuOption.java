package com.europair.management.rest.model.masters.menu.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "menu_option")
@Data
public class MenuOption extends AuditModificationBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column
    private String label;
    @Column
    private String icon;
    @Column
    private String route;

    @ManyToOne
    private MenuOption parent;
    @OneToMany(mappedBy = "parent")
    private List<MenuOption> childs;

}
