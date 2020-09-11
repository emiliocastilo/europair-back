/*
 * Copyright (c) 2020. 2020 Plexus Tech. EuropAir license
 */

package com.europair.management.rest.model.audit.entity;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Audit Revision base class
 * @param <T> Entity under revision
 *
 * @author david.castro
 */
@Data
@NoArgsConstructor
public class AuditRevision<T>  {
	
	private Number rev;
		
	private T entity;
}
