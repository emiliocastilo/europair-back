package com.europair.management.rest.masters.menu.service;

import com.europair.management.api.dto.menu.dto.MenuOptionDto;
import com.europair.management.rest.masters.menu.repository.MenuOptionRepository;
import com.europair.management.rest.model.masters.menu.entity.MenuOption;
import com.europair.management.rest.model.masters.menu.mapper.MenuOptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MenuService {
    private final MenuOptionRepository menuOptionRepository;

    @Autowired
    public MenuService(MenuOptionRepository menuOptionRepository) {
        this.menuOptionRepository = menuOptionRepository;
    }

    public MenuOptionDto getMenu(final Long parentMenuOptionId){
        final Optional<MenuOption> queryResult = this.menuOptionRepository.findById(parentMenuOptionId);
        if(queryResult.isPresent()){
            return MenuOptionMapper.INSTANCE.toDto(queryResult.get());
        } else{
            return null;
        }
    }

}
