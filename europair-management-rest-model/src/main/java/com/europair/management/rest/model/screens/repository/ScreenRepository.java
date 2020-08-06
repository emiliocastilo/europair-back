package com.europair.management.rest.model.screens.repository;

import com.europair.management.rest.model.screens.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {

  List<Screen> findByName(final String name);
}
