package com.europair.management.rest.model.masters.menu.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.screens.entity.Screen;
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
    private Integer weight;

    @Column
    private String icon;

    @Transient
    private String route;

    @ManyToOne
    private MenuOption parent;
    @OneToMany(mappedBy = "parent")
    private List<MenuOption> childs;

    @Column(name = "screen_id", nullable = true)
    private Long screenId;

    @OneToOne
    @JoinColumn(name = "screen_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false)
    private Screen screen;


}
