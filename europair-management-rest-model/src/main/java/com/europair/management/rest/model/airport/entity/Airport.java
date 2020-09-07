package com.europair.management.rest.model.airport.entity;

import com.europair.management.api.dto.conversions.common.Unit;
import com.europair.management.api.enums.UTCEnum;
import com.europair.management.rest.model.audit.entity.SoftRemovableBaseEntity;
import com.europair.management.rest.model.cities.entity.City;
import com.europair.management.rest.model.common.TextField;
import com.europair.management.rest.model.countries.entity.Country;
import com.europair.management.rest.model.enums.CustomsEnum;
import com.europair.management.rest.model.enums.FlightRulesEnum;
import com.europair.management.rest.model.operatorsairports.entity.OperatorsAirports;
import com.europair.management.rest.model.regions.entity.Region;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "airports")
@Data
public class Airport extends SoftRemovableBaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "iata_code", unique = true, length = TextField.IATA_CODE, nullable = false)
    private String iataCode;

    @Column(name = "icao_code", unique = true, length = TextField.ICAO_CODE, nullable = false)
    private String icaoCode;

    @Column(name = "name", length = TextField.TEXT_255, nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @ManyToOne(optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    // ToDo: Set type for timezones
    @Column(name = "time_zone")
    @Enumerated(EnumType.STRING)
    private UTCEnum timeZone;

    @Column(name = "elevation")
    private Double elevation;

    @Column(name = "elevation_unit")
    @Enumerated(EnumType.STRING)
    private Unit elevationUnit;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "customs")
    @Enumerated(EnumType.STRING)
    private CustomsEnum customs;

    @Column(name = "special_conditions")
    private Boolean specialConditions = false;

    @Column(name = "flight_rules")
    @Enumerated(EnumType.STRING)
    private FlightRulesEnum flightRules;

    @Column(name = "canary_islands")
    private Boolean canaryIslands = false;

    @Column(name = "balearics")
    private Boolean balearics = false;

    @OneToMany(orphanRemoval = true, mappedBy = "airport")
    private List<Runway> runways;

    @OneToMany(orphanRemoval = true, mappedBy = "airport")
    private List<Terminal> terminals;

    @OneToMany(orphanRemoval = true, mappedBy = "airport")
    private List<OperatorsAirports> operators;

    @OneToMany(orphanRemoval = true, mappedBy = "airport")
    private List<AirportObservation> observations;

    @ManyToMany(mappedBy = "airports")
    private List<Region> regions;

}
