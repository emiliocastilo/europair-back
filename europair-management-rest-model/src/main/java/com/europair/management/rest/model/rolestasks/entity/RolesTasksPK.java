package com.europair.management.rest.model.rolestasks.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RolesTasksPK implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long roleId;

    private Long taskId;

    public RolesTasksPK() {
        // nothing to do here
    }


    public Long getRoleId() {
        return roleId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
