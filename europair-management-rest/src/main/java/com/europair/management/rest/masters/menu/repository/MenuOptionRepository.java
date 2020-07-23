package com.europair.management.rest.masters.menu.repository;

import com.europair.management.rest.model.masters.menu.entity.MenuOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuOptionRepository extends JpaRepository<MenuOption, Long> {
}
