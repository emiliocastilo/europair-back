package com.europair.management.rest.model.routes.entity;

import com.europair.management.api.enums.FrequencyEnum;
import com.europair.management.api.enums.RouteStatesEnum;
import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntityHardAudited;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.flights.entity.Flight;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "routes")
@Audited
@Getter
@Setter
@RequiredArgsConstructor
public class Route extends AuditModificationBaseEntityHardAudited implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String label;

    @Column
    @Enumerated(EnumType.STRING)
    private FrequencyEnum frequency;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "rotations")
    private Integer rotationsNumber;

    @NotNull
    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @NotAudited
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false, insertable = false, updatable = false)
    private File file;

    @Column(name = "parent_route_id")
    private Long parentRouteId;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "parent_route_id", insertable = false, updatable = false)
    private Route parentRoute;

    @NotAudited
    @OneToMany(mappedBy = "parentRoute", orphanRemoval = true)
    private List<Route> rotations;

    @NotAudited
    @OneToMany(mappedBy = "route", orphanRemoval = true)
    private List<Flight> flights;

    @NotAudited
    @OneToMany(mappedBy = "route", cascade = CascadeType.REMOVE)
    private List<RouteFrequencyDay> frequencyDays;

    @NotAudited
    @OneToMany(mappedBy = "route")
    private Set<Contribution> contributions;

    @Column(name = "has_contributions")
    private Boolean hasContributions = false;

    @Column(name = "route_state")
    @Enumerated(EnumType.STRING)
    private RouteStatesEnum routeState;

}
