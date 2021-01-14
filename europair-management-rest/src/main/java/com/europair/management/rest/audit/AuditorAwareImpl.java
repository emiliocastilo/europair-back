/*
 * Copyright (c) 2020. 2020 Plexus Tech. EuropAir license
 */

package com.europair.management.rest.audit;

import com.europair.management.rest.model.users.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditorAwareImpl.class);


    @Override
    public Optional<String> getCurrentAuditor() {
        LOGGER.debug(String.format("Trying to retrieve userName form SecurityContextHolder for simple and hard audit (Srpign & Envers) : %s ",
                AuditorAwareImpl.class.getCanonicalName()));

        Optional res;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        LOGGER.debug(String.format("%s :  username retrieved from security context -> %s", AuditorAwareImpl.class.getCanonicalName(), username));

        /* At this point we can retrieve de username from SecurityContextHolder,
         this line only has sense if we need more information allocated in data base*/
        //res = userRepository.findByUsername(username).map(u -> u.getUsername());
        res = Optional.of(username);

        return res;

    }
}
