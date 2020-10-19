package com.europair.management.impl.common.service;

import com.europair.management.api.enums.ContributionStates;
import com.europair.management.api.enums.FileStates;
import com.europair.management.api.enums.RouteStates;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.contributions.repository.ContributionRepository;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.files.repository.FileRepository;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class StateChangeServiceImpl implements StateChangeService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private ContributionRepository contributionRepository;

    @Override
    public void changeState(Long fileId, FileStates state) {
        File file = getFile(fileId);
        FileStates currentState = FileStates.NEW_REQUEST; // ToDo: recuperar estado enum en Expediente
        if (fileRepository.canChangeState(currentState, state)) {
            // ToDo: setear estado enum en Expediente
//           file.setState(state);
            fileRepository.save(file);
        } else {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    "File cannot change from state: " + currentState + " to: " + state);
        }
    }

    @Override
    public void changeState(Long routeId, RouteStates state) {
        Route route = getRoute(routeId);
        if (routeRepository.canChangeState(route.getRouteState(), state)) {
            route.setRouteState(state);
            routeRepository.save(route);
            // Change states from other entities
            if (RouteStates.OPTIONED.equals(state)) {
                changeState(route.getFileId(), FileStates.OPTIONED);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    "Route cannot change from state: " + route.getRouteState());
        }
    }

    @Override
    public void changeState(Long contributionId, ContributionStates state) {
        Contribution contribution = getContribution(contributionId);
        if (contributionRepository.canChangeState(contribution.getContributionState(), state)) {
            contribution.setContributionState(state);
            contributionRepository.save(contribution);
            // Change states from other entities
            if (ContributionStates.CONFIRMED.equals(state)) {
                changeState(contribution.getRouteId(), RouteStates.WON);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    "Contribution cannot change from state: " + contribution.getContributionState());
        }
    }

    private File getFile(final Long fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found on id: " + fileId));
    }

    private Route getRoute(final Long routeId) {
        return routeRepository.findById(routeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found on id: " + routeId));
    }

    private Contribution getContribution(final Long contributionId) {
        return contributionRepository.findById(contributionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found on id: " + contributionId));
    }
}
