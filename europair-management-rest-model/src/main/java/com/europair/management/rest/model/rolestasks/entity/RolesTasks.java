package com.europair.management.rest.model.rolestasks.entity;

import com.europair.management.rest.model.roles.entity.Role;
import com.europair.management.rest.model.tasks.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "roles_tasks")
public class RolesTasks implements Serializable{

    @EmbeddedId
    RolesTasksPK id;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    Role role;

    @ManyToOne
    @MapsId("taskId")
    @JoinColumn(name = "task_id")
    Task task;

    public RolesTasks() {
        // nothing to do here
    }

    public Role getRole() {
        return role;
    }

    public Task getTask() {
        return task;
    }

    public RolesTasksPK getId() {
        return id;
    }

    public void setId(RolesTasksPK id) {
        this.id = id;
    }

    public void setRole(Role roles) {
        this.role = roles;
    }

    public void setTask(Task tasks) {
        this.task = tasks;
    }
}
