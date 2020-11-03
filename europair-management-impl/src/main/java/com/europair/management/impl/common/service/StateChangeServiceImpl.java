package com.europair.management.impl.common.service;

import com.europair.management.api.enums.ContributionStatesEnum;
import com.europair.management.api.enums.FileStatesEnum;
import com.europair.management.api.enums.RouteStatesEnum;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.contributions.repository.ContributionRepository;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.files.entity.FileStatus;
import com.europair.management.rest.model.files.repository.FileRepository;
import com.europair.management.rest.model.files.repository.FileStatusRepository;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StateChangeServiceImpl implements IStateChangeService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    private FileStatusRepository fileStatusRepository;

    @Override
    public void changeState(@NotEmpty List<Long> fileIds, FileStatesEnum state) {
        List<File> files = fileRepository.findAllByIdIn(fileIds);
        files = files.stream().map(file -> changeFileState(file, state)).collect(Collectors.toList());
        fileRepository.saveAll(files);
    }

    @Override
    public void changeState(@NotEmpty List<Long> routeIds, RouteStatesEnum state) {
        List<Route> routes = routeRepository.findAllByIdIn(routeIds);
        routes = routes.stream().map(route -> changeRouteState(route, state)).collect(Collectors.toList());
        routeRepository.saveAll(routes);
    }

    @Override
    public void changeState(@NotEmpty final List<Long> contributionIds, ContributionStatesEnum state) {
        List<Contribution> contributions = contributionRepository.findAllByIdIn(contributionIds);
        contributions = contributions.stream().map(contribution -> changeContributionState(contribution, state)).collect(Collectors.toList());
        contributionRepository.saveAll(contributions);
    }

    /**
     * Validates route state change, changes the value if valid, throws exception if not, and checks if has to trigger
     * state changes in other entities
     *
     * @param route Route entity
     * @param state State to set to the route
     * @return Route with updated state
     */
    private Route changeRouteState(final Route route, final RouteStatesEnum state) {
        if (routeRepository.canChangeState(route.getRouteState(), state)) {
            route.setRouteState(state);
            // Change states from other entities
            if (RouteStatesEnum.OPTIONED.equals(state)) {
                changeState(Collections.singletonList(route.getFileId()), FileStatesEnum.OPTIONED);
            }
            if (RouteStatesEnum.WON.equals(state) && !CollectionUtils.isEmpty(route.getRotations())) {
                List<Long> rotationIds = route.getRotations().stream().map(Route::getId).collect(Collectors.toList());
                changeState(rotationIds, RouteStatesEnum.WON);
            }
            return route;
        } else {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    "Route cannot change from state: " + route.getRouteState() + " to: " + state);
        }
    }

    /**
     * Validates file state change, changes the value if valid, throws exception if not, and checks if has to trigger
     * state changes in other entities
     *
     * @param file  File entity
     * @param state State to set to the file
     * @return File with updated state
     */
    private File changeFileState(final File file, final FileStatesEnum state) {
        FileStatesEnum currentState = FileStatesEnum.valueOf(file.getStatus().getCode());
        if (fileRepository.canChangeState(currentState, state)) {
            FileStatus updatedStatus = fileStatusRepository.findFirstByCode(state.toString())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File status not found with code: " + state));
            file.setStatusId(updatedStatus.getId());
            file.setStatus(updatedStatus);
            // Change states from other entities
            if (FileStatesEnum.CNX.equals(state)) {
                List<Long> routeIds = file.getRoutes().stream().map(Route::getId).collect(Collectors.toList());
                changeState(routeIds, FileStatesEnum.OPTIONED);
            }
            return file;
        } else {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    "File cannot change from state: " + currentState + " to: " + state);
        }
    }

    /**
     * Validates contribution state change, changes the value if valid, throws exception if not, and checks if has to trigger
     * state changes in other entities
     *
     * @param contribution Contribution entity
     * @param state        State to set to the contribution
     * @return Contribution with updated state
     */
    private Contribution changeContributionState(final Contribution contribution, final ContributionStatesEnum state) {
        if (contributionRepository.canChangeState(contribution.getContributionState(), state)) {
            contribution.setContributionState(state);
            // Change states from other entities
            if (ContributionStatesEnum.CONFIRMED.equals(state)) {
                changeState(Collections.singletonList(contribution.getRouteId()), RouteStatesEnum.WON);
            }
            return contribution;
        } else {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    "Contribution cannot change from state: " + contribution.getContributionState() + " to: " + state);
        }
    }

}
