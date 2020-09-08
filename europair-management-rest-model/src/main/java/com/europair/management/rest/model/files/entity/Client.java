package com.europair.management.rest.model.files.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.common.TextField;
import com.europair.management.rest.model.countries.entity.Country;
import com.europair.management.rest.model.enums.ClientTypeEnum;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "clients")
@Data
public class Client extends AuditModificationBaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_code", nullable = false, unique = true, length = TextField.TEXT_20)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ClientTypeEnum type = ClientTypeEnum.INDIVIDUAL;

    @Column(name = "canary_islands")
    private Boolean canaryIslands = false;

    @Column(name = "vies")
    private Boolean vies = false;

    @ManyToOne
    private Country country;

}
