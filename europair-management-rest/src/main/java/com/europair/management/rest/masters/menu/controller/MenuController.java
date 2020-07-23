package com.europair.management.rest.masters.menu.controller;

import com.europair.management.rest.masters.menu.service.MenuService;
import com.europair.management.rest.model.masters.menu.dto.MenuOptionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/menu")
public class MenuController {
    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/{parentMenuOptionId}")
    public MenuOptionDto getMenu(@PathVariable final Long parentMenuOptionId){
        return this.menuService.getMenu(parentMenuOptionId);
    }

}