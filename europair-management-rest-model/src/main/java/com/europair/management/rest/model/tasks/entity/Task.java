package com.europair.management.rest.model.tasks.entity;

import com.europair.management.rest.model.roles.entity.Role;
import com.europair.management.rest.model.screens.entity.Screen;
import com.europair.management.rest.model.users.entity.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tasks")
@Data
public class Task implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String name;

  @Column
  private String description;

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(name = "tasks_screens",
              joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
              inverseJoinColumns = @JoinColumn(name = "screen_id", referencedColumnName = "id"))
  private List<Screen> screens;

  @ManyToMany(mappedBy = "tasks")
  private List<Role> roles;

  @ManyToMany(mappedBy = "tasks")
  private List<User> users;

}
