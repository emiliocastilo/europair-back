/*
 * Copyright (c) 2020. 2020 Plexus Tech. EuropAir license
 */

package com.europair.management.api.dto.audit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class AuditModificationBaseDTO implements Serializable {
	
	private static final long serialVersionUID = -4608018056811127775L;
	
	/**
	 * Audit: Creation date @see AuditModificationsBaseEntity.createdAt
	 */
	@Schema(description = "Audit creation date")
	private LocalDate createdAt;
	
	/**
	 * Audit: Creation author @see AuditModificationsBaseEntity.createdBy
	 */
	@Size(min = 0, max = 30, message = "Field name must be 30 character max")
	@Schema(description = "Audit creation author")
	private String createdBy;

	/**
	 * Audit: Modification date @see AuditModificationsBaseEntity.modifiedAt
	 */
	@Schema(description = "Audit modification date")
	private LocalDate modifiedAt;
	
	/**
	 * Audit: Modification author @see AuditModificationsBaseEntity.modifiedBy
	 */
	@Size(min = 0, max = 30, message = "Field name must be 30 character max")
	@Schema(description = "Audit creation author")
	private String modifiedBy;
}
