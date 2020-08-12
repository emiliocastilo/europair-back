package com.europair.management.rest.model.tasks.repository;

import com.europair.management.rest.model.tasks.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, ITaskRepositoryCustom {

  List<Task> findByName(final String name);

}
