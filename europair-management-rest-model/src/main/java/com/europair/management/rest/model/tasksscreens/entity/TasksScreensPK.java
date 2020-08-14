package com.europair.management.rest.model.tasksscreens.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TasksScreensPK implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long taskId;

    private Long screenId;

    public TasksScreensPK() {
        // nothing to do here
    }


    public Long getTaskId() {
        return taskId;
    }

    public Long getScreenId() {
        return screenId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public void setScreenId(Long screenId) {
        this.screenId = screenId;
    }
}
