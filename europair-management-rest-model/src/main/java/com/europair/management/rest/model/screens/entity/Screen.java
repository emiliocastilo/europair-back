package com.europair.management.rest.model.screens.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.tasks.entity.Task;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "screens")
@Data
public class Screen extends AuditModificationBaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String name;

  @Column
  private String description;

/*
  Delete this relationship to avoid inheritance in hibernate and recursively

  @ManyToMany(mappedBy = "screens")
  private List<Task> tasks;*/

  @Column(name = "route")
  private String route;

}
