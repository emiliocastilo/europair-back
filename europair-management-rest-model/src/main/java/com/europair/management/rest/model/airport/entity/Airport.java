package com.europair.management.rest.model.airport.entity;

import com.europair.management.rest.model.cities.entity.City;
import com.europair.management.rest.model.common.entity.Location;
import com.europair.management.rest.model.common.entity.PhysicalQuantity;
import com.europair.management.rest.model.common.entity.SoftRemovableBaseEntity;
import com.europair.management.rest.model.countries.entity.Country;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "airports")
@Data
public class Airport extends SoftRemovableBaseEntity implements Serializable {

    public static enum Customs {
        YES, NO, ON_REQUEST
    }

    public static enum FlightRules {
        IFR, VFR
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String iataCode;

    @Column(unique = true)
    private String icaoCode;

    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "time_zone")
    private String timeZone;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "magnitude", column = @Column(name = "elevation")),
            @AttributeOverride(name = "unit", column = @Column(name = "elevation_unit"))
    })
    private PhysicalQuantity elevation;

    @Embedded
    private Location location;

    @Enumerated(EnumType.STRING)
    private Customs customs;

    private boolean specialConditions = false;

    @Enumerated(EnumType.STRING)
    private FlightRules flightRules;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "airport")
    private List<Runway> runways;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "airport")
    private List<Terminal> terminals;

    // TODO: operadores certificados: códigos del operador certificado y
    //  observaciones. Sólo se habilitará si el campo de condiciones
    //  especiales está marcado.

    @ElementCollection
    private List<String> comments;

    // TODO: directorio del aeropuerto: listado de contactos de todo
    //  tipo que se hayan dado de alta en el módulo de CRM y estén
    //  asociados al aeropuerto: contactos del aeropuerto, contactos
    //  de empresas de handling, etc.

    // TODO: flota del aeropuerto: se podrá consultar la flota asociada
    //  al aeropuerto. Se realizará una consulta al maestro de flota,
    //  filtrando por el aeropuerto a consultar.

    // TODO: regiones: A qué regiones pertenece este aeropuerto.
}
