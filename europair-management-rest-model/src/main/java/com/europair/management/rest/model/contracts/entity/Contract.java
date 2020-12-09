package com.europair.management.rest.model.contracts.entity;

import com.europair.management.api.dto.common.TextField;
import com.europair.management.api.enums.ContractStatesEnum;
import com.europair.management.api.enums.PurchaseSaleEnum;
import com.europair.management.rest.model.audit.entity.SoftRemovableBaseEntityHardAudited;
import com.europair.management.rest.model.files.entity.Client;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.files.entity.Provider;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "contracts")
@Audited
@Data
public class Contract extends SoftRemovableBaseEntityHardAudited implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = TextField.TEXT_20, nullable = false, unique = true)
    private String code;

    @Column
    private String description;

    @NotNull
    @Column(name = "contract_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PurchaseSaleEnum contractType;

    @NotNull
    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @NotAudited
    private File file;

    @Column(name = "client_id")
    private Long clientId;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", insertable = false, updatable = false)
    @NotAudited
    private Client client;

    @Column(name = "provider_id")
    private Long providerId;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "provider_id", referencedColumnName = "id", insertable = false, updatable = false)
    @NotAudited
    private Provider provider;

    @NotNull
    @Column(name = "contract_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private ContractStatesEnum contractState;

    @NotAudited
    @OneToMany(mappedBy = "contract", orphanRemoval = true)
    private Set<ContractLine> contractLines;

    @Column(name = "contract_date")
    private LocalDateTime contractDate;

    @Column(name = "signature_date")
    private LocalDateTime signatureDate;

    // ToDo: one to one or one to many?
    @OneToOne(mappedBy = "contract", orphanRemoval = true)
    private ContractConfiguration contractConfiguration;
}
