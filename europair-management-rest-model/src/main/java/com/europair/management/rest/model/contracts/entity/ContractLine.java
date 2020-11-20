package com.europair.management.rest.model.contracts.entity;

import com.europair.management.api.enums.PurchaseSaleEnum;
import com.europair.management.api.enums.ServiceTypeEnum;
import com.europair.management.rest.model.audit.entity.SoftRemovableBaseEntityHardAudited;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.flights.entity.Flight;
import com.europair.management.rest.model.routes.entity.Route;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

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

@Entity
@Table(name = "contract_lines")
@Audited
@Data
public class ContractLine extends SoftRemovableBaseEntityHardAudited implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contract_id")
    private Long contractId;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "contract_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private Contract contract;

    @Column(name = "route_id")
    private Long routeId;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "id", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private Route route;

    @Column(name = "flight_id")
    private Long flightId;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "flight_id", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private Flight flight;

    @Column(name = "comments", length = 255)
    private String comments;

    @Column(name = "price", precision = 12, scale = 4)
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "included_vat")
    private Boolean includedVAT;

    @Column(name = "contract_line_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PurchaseSaleEnum contractLineType;

//    // poner TIPO_SERVICIO : vuelo, catering, son los tipos que ya tenemos creados
//    @Column(name = "service_type", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private ServiceTypeEnum type;

    public ContractLine() {
    }

    /**
     * Copy constructor to copy all properties but id, and object entities.
     *
     * @param lineContributionRoute Entity to copy params from
     */
//    public ContractLine(ContractLine lineContributionRoute) {
//        this.contributionId = lineContributionRoute.getContributionId();
//        this.comments = lineContributionRoute.getComments();
//        this.routeId = lineContributionRoute.getRouteId();
//        this.price = lineContributionRoute.getPrice();
//        this.includedVAT = lineContributionRoute.getIncludedVAT();
//        this.lineContributionRouteType = lineContributionRoute.getLineContributionRouteType();
//        this.type = lineContributionRoute.getType();
//    }
}
