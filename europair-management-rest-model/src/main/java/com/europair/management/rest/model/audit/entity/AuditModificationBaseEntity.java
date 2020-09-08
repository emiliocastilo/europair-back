/*
 * Copyright (c) 2020. 2020 Plexus Tech. EuropAir license
 */

package com.europair.management.rest.model.audit.entity;

import com.europair.management.rest.model.common.TextField;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Audit Modification Base Entity
 * This parent abstract class must be inherited by any base audit class. It provides standard auditory fields:
 *    - Creation stamps (user & date)
 *    - Last modification stamps ( user & date)
 *
 *  * @author david.castro
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@Data
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditModificationBaseEntity {

    /**
     * Audit: Creation date @see AuditModificationsBaseEntity.createdAt
     */
    @Column(nullable = false, name = "created_at")
    @CreatedDate
    private LocalDate createdAt;

    /**
     * Audit: Modification date @see AuditModificationsBaseEntity.modifiedAt
     */
    @Column(nullable = true, name = "modified_at")
    @LastModifiedDate
    private LocalDate modifiedAt;

    /**
     * Audit: Creation author @see AuditModificationsBaseEntity.createdBy
     */
    @Column(nullable = false, name = "created_by", length = TextField.TEXT_30)
    @CreatedBy
    private String createdBy;

    /**
     * Audit: Modification author @see AuditModificationsBaseEntity.modifiedBy
     */
    @Column(nullable = true, name = "modified_by", length = TextField.TEXT_30)
    @LastModifiedBy
    private String modifiedBy;
}
