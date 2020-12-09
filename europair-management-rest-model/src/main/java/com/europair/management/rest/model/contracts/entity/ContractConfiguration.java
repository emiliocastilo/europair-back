package com.europair.management.rest.model.contracts.entity;

import com.europair.management.api.enums.CurrencyEnum;
import com.europair.management.api.enums.UTCEnum;
import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntityHardAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contract_configurations")
@Audited
@Data
public class ContractConfiguration extends AuditModificationBaseEntityHardAudited implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "contract_id", nullable = false)
    private Long contractId;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "contract_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Contract contract;

    @Column(name = "language")
    private String language;

    @Column(name = "timezone")
    @Enumerated(EnumType.STRING)
    private UTCEnum timezone;

    @Column(name = "contract_payment_conditions_id")
    private Long paymentConditionsId;

    @NotAudited
    @OneToOne
    @JoinColumn(name = "contract_payment_conditions_id", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ContractPaymentCondition paymentConditions;

    @Column(name = "contract_payment_conditions_observation", length = 255)
    private String paymentConditionsObservation;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;

    @Column(name = "deposit", precision = 12, scale = 4)
    private BigDecimal deposit;

    @Column(name = "deposit_expiration_date")
    private LocalDate depositExpirationDate;

}
