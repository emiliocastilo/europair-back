/*
 * Copyright (c) 2020. 2020 Plexus Tech. EuropAir license
 */

package com.europair.management.rest.audit;

import com.europair.management.rest.model.users.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Autowired
    private IUserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditorAwareImpl.class);


    @Override
    public Optional<String> getCurrentAuditor() {

        Optional res;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        LOGGER.debug("AuditorAwareImpl :  username retrieved from security context -> " + username);

        res = userRepository.findByUsername(username).map(u -> u.getUsername());

        return res;

    }
}
