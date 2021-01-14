package com.europair.management.rest.model.users.entity;

import com.europair.management.api.enums.UTCEnum;
import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.roles.entity.Role;
import com.europair.management.rest.model.tasks.entity.Task;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="users")
@Data
public class User extends AuditModificationBaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String username;

  private String password;

  private String name;

  private String surname;

  private String email;

  @Column(name = "time_zone")
  @Enumerated(EnumType.STRING)
  private UTCEnum timeZone;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "users_roles",
              joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
              inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private List<Role> roles;

  @ManyToMany
  @JoinTable(name = "users_tasks",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"))
  private List<Task> tasks;

  @Column(name = "internal_user")
  private Boolean internalUser;
}
