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

        // Audit for External users
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        LOGGER.debug("AuditorAwareImpl :  username -> " + username);

        res = userRepository.findByUsername(username).map(u -> u.getUsername());

        // Audit for Azure users
        if (res.isEmpty()) {

            LOGGER.debug("AuditorAwareImpl intentando recuperar usuario de azure : ");

            String emailAzure = (String) ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaims().get("unique_name");

            LOGGER.debug("AuditorAwareImpl intentando recuperar usuario de azure con email : " + emailAzure);

            try {


                Optional<String> azureUserName = userRepository.findByEmail(emailAzure).map(u -> u.getUsername());


                if (azureUserName.isEmpty()) {
                    LOGGER.debug("AuditorAwareImpl NO RECUPERADO USER DE AZURE");
                }
                LOGGER.debug("tanto si encuentra como si no:" + azureUserName.get());


                res = azureUserName;
            } catch (Exception ex) {
                LOGGER.debug("EXCEPTION + " + ex.getMessage());
                LOGGER.debug("EXCEPTION + ", ex);
            }
        }

        return res;

    }
}
