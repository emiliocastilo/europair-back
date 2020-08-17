package com.europair.management.rest.model.airport.entity;

import com.europair.management.rest.model.audit.entity.SoftRemovableBaseEntity;
import com.europair.management.rest.model.cities.entity.City;
import com.europair.management.rest.model.common.TextField;
import com.europair.management.rest.model.countries.entity.Country;
import com.europair.management.rest.model.enums.CustomsEnum;
import com.europair.management.rest.model.enums.FlightRulesEnum;
import com.europair.management.rest.model.enums.UnitEnum;
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
import java.util.Set;

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
    private String timeZone;

    @Column(name = "elevation")
    private Double elevation;

    @Column(name = "elevation_unit")
    @Enumerated(EnumType.STRING)
    private UnitEnum elevationUnit;

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

    @OneToMany(orphanRemoval = true, mappedBy = "airport")
    private Set<Runway> runways;

    @OneToMany(orphanRemoval = true, mappedBy = "airport")
    private Set<Terminal> terminals;

    // TODO: operadores certificados: códigos del operador certificado y
    //  observaciones. Sólo se habilitará si el campo de condiciones
    //  especiales está marcado.

    // ToDo: Observaciones del aeropuerto.

    // TODO: directorio del aeropuerto: listado de contactos de todo
    //  tipo que se hayan dado de alta en el módulo de CRM y estén
    //  asociados al aeropuerto: contactos del aeropuerto, contactos
    //  de empresas de handling, etc.

    // TODO: flota del aeropuerto: se podrá consultar la flota asociada
    //  al aeropuerto. Se realizará una consulta al maestro de flota,
    //  filtrando por el aeropuerto a consultar.

    // TODO: regiones: A qué regiones pertenece este aeropuerto.
}
