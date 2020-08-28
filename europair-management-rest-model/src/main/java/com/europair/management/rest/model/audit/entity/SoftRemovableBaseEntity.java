package com.europair.management.rest.model.audit.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Data
public abstract class SoftRemovableBaseEntity extends AuditModificationBaseEntity {

    public static final String REMOVED_AT = "removedAt";

    @Column(name = "removed_at", nullable = true)
    private Date removedAt;
}
