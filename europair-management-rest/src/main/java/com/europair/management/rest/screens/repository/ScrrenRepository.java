package com.europair.management.rest.screens.repository;

import com.europair.management.rest.screens.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrrenRepository extends JpaRepository<Screen, Long> {
}
