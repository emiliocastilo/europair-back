package com.europair.management.rest.model.tasksscreens.repository;

import com.europair.management.rest.model.rolestasks.entity.RolesTasks;
import com.europair.management.rest.model.tasksscreens.entity.TasksScreens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksScreensRepository extends JpaRepository<TasksScreens, Long> {

    //nothing here just only to have .save .delete .findOne .count .findAll , etc
}
