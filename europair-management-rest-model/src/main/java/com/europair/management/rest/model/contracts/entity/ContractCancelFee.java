package com.europair.management.rest.model.contracts.entity;

import com.europair.management.api.dto.common.TextField;
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
@Table(name = "contract_cancel_fees")
@Audited
@Data
public class ContractCancelFee extends AuditModificationBaseEntityHardAudited implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_value")
    private Double fromValue;

    @Column(name = "from_unit", length = TextField.TEXT_40)
    private String fromUnit;

    @Column(name = "fee_percentage")
    private Double feePercentage;

}
