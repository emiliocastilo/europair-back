package com.europair.management.rest.model.rotations.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.flights.entity.Flight;
import com.europair.management.rest.model.routes.entity.Route;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "rotations")
@Data
public class Rotation extends AuditModificationBaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String journey;

  @Column(name = "operation_start_date")
  private LocalDate operationStartDate;

  @Column(name = "operation_end_date")
  private LocalDate operationEndDate;

  @Column(name = "total_seats")
  private Integer totalSeats;

  @ManyToOne
  private List<Flight> flights;

  @ManyToOne(optional = false)
  @JoinColumn(name = "route_id", nullable = false)
  private Route route;

}
