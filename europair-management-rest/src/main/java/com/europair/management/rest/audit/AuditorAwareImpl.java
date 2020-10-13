/*
 * Copyright (c) 2020. 2020 Plexus Tech. EuropAir license
 */

package com.europair.management.rest.audit;

import com.europair.management.rest.model.users.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Autowired
    private IUserRepository userRepository;


    @Override
    public Optional<String> getCurrentAuditor() {

        Optional res;

        // Audit for External users
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        res = userRepository.findByUsername(username).map(u -> u.getUsername());

        // Audit for Azure users
        if ( res.isEmpty()) {
            String emailAzure = (String) ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaims().get("unique_name");
            Optional <String> azureUserName = userRepository.findByEmail(emailAzure).map(u -> u.getUsername());
            res= azureUserName;
        }

        return res;

    }
}
