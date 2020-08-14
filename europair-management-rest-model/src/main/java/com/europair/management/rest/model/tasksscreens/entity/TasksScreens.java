package com.europair.management.rest.model.tasksscreens.entity;

import com.europair.management.rest.model.roles.entity.Role;
import com.europair.management.rest.model.rolestasks.entity.RolesTasksPK;
import com.europair.management.rest.model.screens.entity.Screen;
import com.europair.management.rest.model.tasks.entity.Task;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "tasks_screens")
public class TasksScreens implements Serializable{

    @EmbeddedId
    TasksScreensPK id;

    @ManyToOne
    @MapsId("taskId")
    @JoinColumn(name = "task_id")
    Task task;

    @ManyToOne
    @MapsId("screenId")
    @JoinColumn(name = "screen_id")
    Screen screen;

    public TasksScreens() {
        // nothing to do here
    }

    public TasksScreensPK getId() {
        return id;
    }

    public void setId(TasksScreensPK id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    @Override
    public String toString() {
        return "TasksScreens{" +
                "id=" + id +
                ", task=" + task +
                ", screen=" + screen +
                '}';
    }
}
