package com.europair.management.rest.model.contributions.entity;

import com.europair.management.api.enums.LineContributionRouteType;
import com.europair.management.api.enums.ServiceTypeEnum;
import com.europair.management.rest.model.audit.entity.SoftRemovableBaseEntityHardAudited;
import com.europair.management.rest.model.routes.entity.Route;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * This entity only relates contributions with rotations and aditional services with route
 * note: rotation == route without parentRoute
 *
 * Las lineasCotizacionRuta de servicios adicionales que se añadan van a ir asociadas a la ruta como primera instancia
 * En el caso de que se deseen asociar a una ruta tendrán que modificarse.
 */
@Entity
@Table(name = "lines_contribution_route")
@Data
public class LineContributionRoute extends SoftRemovableBaseEntityHardAudited implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contribution_id")
    private Long contributionId;

    @ManyToOne
    @JoinColumn(name = "contribution_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private Contribution contribution;

    @Column(name = "route_id")
    private Long routeId;

    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private Route route;

    @Column(name = "comments", length = 255)
    private String comments;

    @Column(name = "price", precision = 12, scale = 4)
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "included_vat")
    private Boolean includedVAT;

    //Poner TIPO : compra o venta
    @Column(name = "line_contribution_route_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private LineContributionRouteType lineContributionRouteType;

    // poner TIPO_SERVICIO : vuelo, catering, son los tipos que ya tenemos creados
    @Column(name = "service_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ServiceTypeEnum type;

    public LineContributionRoute() {
    }

    /**
     * Copy constructor to copy all properties but id, and object entities.
     *
     * @param lineContributionRoute Entity to copy params from
     */
    public LineContributionRoute(LineContributionRoute lineContributionRoute) {
        this.contributionId = lineContributionRoute.getContributionId();
        this.comments = lineContributionRoute.getComments();
        this.routeId = lineContributionRoute.getRouteId();
        this.price = lineContributionRoute.getPrice();
        this.includedVAT = lineContributionRoute.getIncludedVAT();
        this.lineContributionRouteType = lineContributionRoute.getLineContributionRouteType();
        this.type = lineContributionRoute.getType();
    }
}
