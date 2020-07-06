package com.europair.management.rest.screens.repository;

import com.europair.management.rest.model.screens.entity.Screen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrrenRepository extends JpaRepository<Screen, Long> {

  List<Screen> findByName(final String name);
}
