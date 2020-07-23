package com.europair.management.rest.model.roles.entity;

import com.europair.management.rest.model.tasks.entity.Task;
import com.europair.management.rest.model.users.entity.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class Role implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String name;

  @Column
  private String description;

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(name = "roles_tasks",
              joinColumns = @JoinColumn(name="role_id", referencedColumnName = "id"),
              inverseJoinColumns = @JoinColumn(name="task_id", referencedColumnName = "id"))
  private List<Task> tasks;

  @ManyToMany(mappedBy = "roles")
  private List<User> users;

}
