package com.europair.management.rest.model.fleet.entity;

import com.europair.management.api.dto.conversions.common.Unit;
import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.audit.entity.SoftRemovableBaseEntity;
import com.europair.management.rest.model.common.TextField;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "aircraft_types")
@Data
public class AircraftType extends SoftRemovableBaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "icao_code", unique = true, length = TextField.ICAO_CODE)
    private String icaoCode;

    @Column(name = "iata_code", unique = true, nullable = true, length = TextField.IATA_CODE)
    private String iataCode;

    @Column(name = "type_code", unique = true, nullable = true, length = TextField.TEXT_20)
    private String code;

    @Column(name = "description", length = TextField.TEXT_255, nullable = false)
    private String description;

    @Column(name = "manufacturer", length = TextField.TEXT_120, nullable = false)
    private String manufacturer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private AircraftCategory category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "subcategory_id")
    private AircraftCategory subcategory;

    @Column(name = "flight_range")
    private Double flightRange;

    @Column(name = "flight_range_unit")
    @Enumerated(EnumType.STRING)
    private Unit flightRangeUnit = Unit.NAUTIC_MILE;

    @Column(name = "cabin_width")
    private Double cabinWidth;

    @Column(name = "cabin_width_unit")
    @Enumerated(EnumType.STRING)
    private Unit cabinWidthUnit;

    @Column(name = "cabin_height")
    private Double cabinHeight;

    @Column(name = "cabin_height_unit")
    @Enumerated(EnumType.STRING)
    private Unit cabinHeightUnit;

    @Column(name = "cabin_length")
    private Double cabinLength;

    @Column(name = "cabin_length_unit")
    @Enumerated(EnumType.STRING)
    private Unit cabinLengthUnit;

    @Column(name = "max_cargo")
    private Double maxCargo;

    @OneToMany(orphanRemoval = true, mappedBy = "aircraftType")
    private List<AircraftTypeAverageSpeed> averageSpeed;

    @OneToMany(orphanRemoval = true, mappedBy = "aircraftType")
    private List<AircraftTypeObservation> observations;
}
