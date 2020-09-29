package com.europair.management.impl.integrations.office365.service;

import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.contributions.repository.ContributionRepository;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class Office365ServiceImpl implements IOffice365Service {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private ContributionRepository contributionRepository;


    @Override
    public void confirmOperation(Long routeId, Long contributionId) {

        // ToDo: get data
        Route route = routeRepository.findById(routeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId));

        Contribution contribution = contributionRepository.findById(routeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found with id: " + contributionId));


        // ToDo: convert


        // ToDo: send data


    }

}
