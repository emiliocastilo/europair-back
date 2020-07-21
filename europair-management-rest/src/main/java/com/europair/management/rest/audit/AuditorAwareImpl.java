/*
 * Copyright (c) 2020. 2020 Plexus Tech. EuropAir license
 */

package com.europair.management.rest.audit;

import com.europair.management.rest.model.users.dto.UserDTO;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        //return Optional.of(personRepo.findByUserPrincipalName(SecurityContextHolder.getContext().getAuthentication().getName()
        // + "@email.com"));
        return Optional.of("AUDIT_TEST_USER");
    }
}
