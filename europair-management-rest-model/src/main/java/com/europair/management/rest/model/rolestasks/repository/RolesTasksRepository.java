package com.europair.management.rest.model.rolestasks.repository;

import com.europair.management.rest.model.rolestasks.entity.RolesTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesTasksRepository extends JpaRepository<RolesTasks, Long> {

    //nothing here just only to have .save .delete .findOne .count .findAll , etc
}
