package com.europair.management.rest.model.contracts.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntityHardAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "contract_conditions")
@Audited
@Data
public class ContractCondition extends AuditModificationBaseEntityHardAudited implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contract_id")
    private Long contractId;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "contract_id", referencedColumnName = "id", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Contract contract;

    @Column(name = "condition_order")
    private Integer conditionOrder;

    @Column(name = "title")
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

}
