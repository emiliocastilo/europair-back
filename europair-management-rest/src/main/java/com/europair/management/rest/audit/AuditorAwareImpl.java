/*
 * Copyright (c) 2020. 2020 Plexus Tech. EuropAir license
 */

package com.europair.management.rest.audit;

import com.europair.management.rest.model.users.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Autowired
    private IUserRepository userRepository;


    @Override
    public Optional<String> getCurrentAuditor() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional auditoryOptionalForExternal = userRepository.findByUsername(username).map(u -> u.getName() + " " + u.getSurname());
        Optional defectOptionalForInternal = Optional.ofNullable(username.substring(0,username.length()%10));
        return (auditoryOptionalForExternal.isEmpty() ? defectOptionalForInternal : auditoryOptionalForExternal);

    }
}
