package com.europair.management.rest.model.contracts.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntityHardAudited;
import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "contract_payment_conditions")
@Audited
@Data
public class ContractPaymentCondition extends AuditModificationBaseEntityHardAudited implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

}
