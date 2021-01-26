package com.europair.management.impl.common.service;

import com.europair.management.api.enums.ContractStatesEnum;
import com.europair.management.api.enums.ContributionStatesEnum;
import com.europair.management.api.enums.FileStatesEnum;
import com.europair.management.api.enums.RouteStatesEnum;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.contracts.entity.Contract;
import com.europair.management.rest.model.contracts.repository.ContractRepository;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.contributions.repository.ContributionRepository;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.files.entity.FileStatus;
import com.europair.management.rest.model.files.repository.FileRepository;
import com.europair.management.rest.model.files.repository.FileStatusRepository;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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

    @Autowired
    private ContractRepository contractRepository;

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

    @Override
    public void changeState(@NotEmpty List<Long> contractIds, ContractStatesEnum state) {
        List<Contract> contracts = contractRepository.findAllByIdIn(contractIds);
        contracts = contracts.stream().map(contract -> changeContractState(contract, state)).collect(Collectors.toList());
        contractRepository.saveAll(contracts);
    }

    @Override
    public boolean canChangeState(@NotNull File file, FileStatesEnum stateTo) {
        FileStatesEnum currentState = FileStatesEnum.valueOf(file.getStatus().getCode());
        // Validate state to change
        boolean canChange = switch (currentState) {
            case NEW_REQUEST -> FileStatesEnum.SALES.equals(stateTo);
            case SALES -> FileStatesEnum.OPTIONED.equals(stateTo) || FileStatesEnum.BLUE_BOOKED.equals(stateTo) || FileStatesEnum.CNX.equals(stateTo);
            case OPTIONED -> FileStatesEnum.BLUE_BOOKED.equals(stateTo) || FileStatesEnum.CNX.equals(stateTo);
            case BLUE_BOOKED -> FileStatesEnum.GREEN_BOOKED.equals(stateTo) || FileStatesEnum.CNX.equals(stateTo)
                    || (FileStatesEnum.BLUE_BOOKED.equals(stateTo) && Boolean.TRUE.equals(file.getUpdatedAfterContractSigned()));
            case GREEN_BOOKED -> FileStatesEnum.PREFLIGHT.equals(stateTo) || FileStatesEnum.CNX.equals(stateTo)
                    || (FileStatesEnum.GREEN_BOOKED.equals(stateTo) && Boolean.TRUE.equals(file.getUpdatedAfterContractSigned()));
            default -> false;
        };
        // Additional validations
        if ((FileStatesEnum.BLUE_BOOKED.equals(stateTo) || FileStatesEnum.GREEN_BOOKED.equals(stateTo)) &&
                file.getRoutes().stream().noneMatch(route -> RouteStatesEnum.WON.equals(route.getRouteState()))) {
            canChange = false;
        }

        return canChange;
    }

    @Override
    public boolean canChangeState(@NotNull Route route, RouteStatesEnum stateTo) {
        RouteStatesEnum currentState = route.getRouteState();
        // Validate state to change
        return switch (currentState) {
            case SALES -> true;
            case OPTIONED -> !RouteStatesEnum.SALES.equals(stateTo);
            case WON -> !RouteStatesEnum.SALES.equals(stateTo) && !RouteStatesEnum.OPTIONED.equals(stateTo);
            default -> false;
        };
    }

    @Override
    public boolean canChangeState(@NotNull Contribution contribution, ContributionStatesEnum stateTo) {
        ContributionStatesEnum currentState = contribution.getContributionState();
        // Validate state to change
        return switch (currentState) {
            case PENDING, SENT -> !ContributionStatesEnum.PENDING.equals(stateTo);
            case QUOTED -> ContributionStatesEnum.WON.equals(stateTo);
            default -> false;
        };
    }

    @Override
    public boolean canChangeState(@NotNull Contract contract, ContractStatesEnum stateTo) {
        ContractStatesEnum currentState = contract.getContractState();
        // Validate state to change
        return switch (currentState) {
            case PENDING -> true;
            case SIGNED -> ContractStatesEnum.CANCELED.equals(stateTo);
            default -> false;
        };
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
        if (canChangeState(route, state)) {
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
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.STATE_CHANGE_ROUTE,
                    String.format("from %s to %s", route.getRouteState(), state));
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
        if (canChangeState(file, state)) {
            FileStatus updatedStatus = fileStatusRepository.findFirstByCode(state.toString())
                    .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.STATE_FILE_STATATUS_NOT_FOUND, state.toString()));
            file.setStatusId(updatedStatus.getId());
            file.setStatus(updatedStatus);
            // Change states from other entities
            if (FileStatesEnum.CNX.equals(state)) {
                List<Long> routeIds = file.getRoutes().stream().map(Route::getId).collect(Collectors.toList());
                changeState(routeIds, RouteStatesEnum.LOST_CANX_REQUEST);
            }
            return file;
        } else {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.STATE_CHANGE_FILE,
                    String.format("from %s to %s", currentState, state));
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
        if (canChangeState(contribution, state)) {
            contribution.setContributionState(state);
            // Change states from other entities
            if (ContributionStatesEnum.WON.equals(state)) {
                changeState(Collections.singletonList(contribution.getRouteId()), RouteStatesEnum.WON);
            }
            return contribution;
        } else {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.STATE_CHANGE_CONTRIBUTION,
                    String.format("from %s to %s", contribution.getContributionState(), state));
        }
    }

    /**
     * Validates contract state change, changes the value if valid, throws exception if not, and checks if has to trigger
     * state changes in other entities
     *
     * @param contract Contract entity
     * @param state    State to set to the contract
     * @return Contract with updated state
     */
    private Contract changeContractState(final Contract contract, final ContractStatesEnum state) {
        if (canChangeState(contract, state)) {
            contract.setContractState(state);
            if (ContractStatesEnum.SIGNED.equals(state) && contract.getSignatureDate() == null) {
                contract.setSignatureDate(LocalDateTime.now());
            }
            // Change states from other entities
            return contract;
        } else {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.STATE_CHANGE_CONTRACT,
                    String.format("from %s to %s", contract.getContractState(), state));
        }
    }

}
