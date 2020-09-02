package com.europair.management.rest.model.rotations.repository;

import com.europair.management.rest.model.rotations.entity.Rotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRotationRepository extends JpaRepository<Rotation, Long> {
}
