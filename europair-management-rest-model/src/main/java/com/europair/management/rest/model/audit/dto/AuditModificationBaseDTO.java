/*
 * Copyright (c) 2020. 2020 Plexus Tech. EuropAir license
 */

package com.europair.management.rest.model.audit.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AuditModificationBaseDTO implements Serializable {
	
	private static final long serialVersionUID = -4608018056811127775L;
	
	/**
	 * Audit: Creation date @see AuditModificationsBaseEntity.createdAt
	 */
	private Date createdAt;
	
	/**
	 * Audit: Creation author @see AuditModificationsBaseEntity.createdBy
	 */
	private String createdBy;

	/**
	 * Audit: Modification date @see AuditModificationsBaseEntity.modifiedAt
	 */
	private Date modifiedAt;
	
	/**
	 * Audit: Modification author @see AuditModificationsBaseEntity.modifiedBy
	 */
	private String modifiedBy;
}
