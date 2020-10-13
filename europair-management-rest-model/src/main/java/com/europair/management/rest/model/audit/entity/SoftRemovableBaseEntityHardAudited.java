package com.europair.management.rest.model.audit.entity;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@Audited
public abstract class SoftRemovableBaseEntityHardAudited extends AuditModificationBaseEntityHardAudited {

    public static final String REMOVED_AT = "removedAt";

    @Column(name = "removed_at", nullable = true)
    private LocalDateTime removedAt;
}
