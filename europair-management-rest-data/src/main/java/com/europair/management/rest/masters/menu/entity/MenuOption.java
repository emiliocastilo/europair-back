package com.europair.management.rest.masters.menu.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "menu_option")
@Data
public class MenuOption {

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
