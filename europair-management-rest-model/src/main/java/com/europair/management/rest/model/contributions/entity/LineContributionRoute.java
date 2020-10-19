package com.europair.management.rest.model.contributions.entity;

import com.europair.management.api.enums.*;
import com.europair.management.rest.model.audit.entity.SoftRemovableBaseEntityHardAudited;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import com.europair.management.rest.model.operators.entity.Operator;
import com.europair.management.rest.model.routes.entity.Route;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * This entity only relates contributions with rotations and aditional services with route
 * note: rotation == route without parentRoute
 *
 * Las lineasCotizacionRuta de servicios adicionales que se añadan van a ir asociadas a la ruta como primera instancia
 * En el caso de que se deseen asociar a una ruta tendrán que modificarse.
 */
@Entity
@Table(name = "contributionlines_route")
@Data
public class LineContributionRoute extends SoftRemovableBaseEntityHardAudited implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contribution_id")
    private Long contributionId;

    @ManyToOne
    @JoinColumn(name = "contribution_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Contribution contribution;

    @Column(name = "route_id")
    private Long routeId;

    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Route route;

    @Column(name = "comments", length = 255)
    private String comments;

    @Column(name = "purchase_price", precision = 12, scale = 4)
    private BigDecimal price;

    @Column(name = "included_vat")
    private Boolean includedVAT;

    //Poner TIPO : compra o venta
    @Column(name = "line_contribution_route_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeLineContributionRoute typeLineContributionRoute;

    // poner TIPO_SERVICIO : vuelo, catering, son los tipos que ya tenemos creados
    @Column(name = "service_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ServiceTypeEnum type;

}
