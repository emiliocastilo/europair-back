package com.europair.management.rest.model.files.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.common.TextField;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "providers")
@Data
public class Provider extends AuditModificationBaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provider_code", nullable = false, unique = true, length = TextField.TEXT_20)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

}