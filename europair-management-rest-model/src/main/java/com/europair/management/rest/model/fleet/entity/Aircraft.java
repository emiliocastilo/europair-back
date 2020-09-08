package com.europair.management.rest.model.fleet.entity;

import com.europair.management.rest.model.audit.entity.SoftRemovableBaseEntity;
import com.europair.management.rest.model.common.TextField;
import com.europair.management.rest.model.contributionaircraft.entity.ContributionAircraft;
import com.europair.management.rest.model.operators.entity.Operator;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "aircrafts")
@Data
public class Aircraft extends SoftRemovableBaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "operator_id", nullable = false)
    private Operator operator;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aircraft_type_id", nullable = false)
    private AircraftType aircraftType;

    @OneToMany(mappedBy = "aircraft", orphanRemoval = true)
    private List<AircraftBase> bases;

    @Column(name = "plate_number", length = TextField.TEXT_20)
    private String plateNumber;

    @Column(name = "production_year")
    private Integer productionYear;

    private Integer quantity;

    @Column(name = "insurance_end_date")
    private Date insuranceEndDate;

    private Boolean ambulance;

    @Column(name = "seating_f")
    private Integer seatingF;

    @Column(name = "seating_c")
    private Integer seatingC;

    @Column(name = "seating_y")
    private Integer seatingY;

    // Total number of seats/beds for nighttime travels
    @Column(name = "nighttime_configuration")
    private Integer nighttimeConfiguration;

    @Column(name = "notes", length = TextField.TEXT_255)
    private String notes;

    // ToDo: tags

    @Column(name = "inside_upgrade_year")
    private Integer insideUpgradeYear;

    @Column(name = "outside_upgrade_year")
    private Integer outsideUpgradeYear;

    @OneToMany(mappedBy = "aircraft", orphanRemoval = true)
    private List<AircraftObservation> observations;

    @OneToMany(mappedBy = "aircraft")
    private Set<ContributionAircraft> contributionAircrafts;
}
