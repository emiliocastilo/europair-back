package com.europair.management.rest.model.routes.entity;

import com.europair.management.api.enums.FrequencyEnum;
import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.flights.entity.Flight;
import lombok.Data;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "routes")
@Data
public class Route extends AuditModificationBaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String label;

    @Column
    @Enumerated(EnumType.STRING)
    private FrequencyEnum frequency;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "rotations")
    private Integer rotationsNumber;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    @ManyToOne
    @JoinColumn(name = "parent_route_id")
    private Route parentRoute;

    @OneToMany(mappedBy = "parentRoute", orphanRemoval = true)
    private List<Route> rotations;

    @OneToMany(mappedBy = "route", orphanRemoval = true)
    private List<Flight> flights;

    @OneToMany(mappedBy = "route", cascade = CascadeType.REMOVE)
    private List<RouteFrequencyDay> frequencyDays;

    @OneToMany(mappedBy = "route")
    private Set<Contribution> contributionSet;


}
