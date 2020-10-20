package com.europair.management.impl.service.contribution;

import com.europair.management.api.dto.contribution.ContributionDTO;
import com.europair.management.api.enums.ContributionStates;
import com.europair.management.impl.common.service.IStateChangeService;
import com.europair.management.impl.mappers.contributions.IContributionMapper;
import com.europair.management.impl.service.flights.IFlightTaxService;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.contributions.repository.ContributionRepository;
import com.europair.management.rest.model.flights.entity.FlightTax;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ContributionServiceImpl implements IContributionService {

    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private IFlightTaxService flightTaxService;

    @Autowired
    private IStateChangeService stateChangeService;

    @Override
    public Page<ContributionDTO> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return contributionRepository.findContributionByCriteria(criteria, pageable).map(IContributionMapper.INSTANCE::toDto);
    }

    @Override
    public ContributionDTO findById(Long id) {
        return IContributionMapper.INSTANCE.toDto(contributionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found with id: " + id)));
    }

    @Override
    public ContributionDTO saveContribution(ContributionDTO contributionDTO) {
        if (contributionDTO.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New Contribution expected. Identifier %s got", contributionDTO.getId()));
        }

        Contribution contribution = IContributionMapper.INSTANCE.toEntity(contributionDTO);
        contribution = contributionRepository.save(contribution);

        // must activate flag in route to indicate the route has a contribution
        final Long routeId = contribution.getRouteId();
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId));
        route.setHasContributions(true);
        routeRepository.save(route);

        // Add flight taxes for the contribution
        List<FlightTax> flightTaxes = flightTaxService.saveFlightTaxes(contribution, route);

        return IContributionMapper.INSTANCE.toDto(contribution);
    }

    @Override
    public ContributionDTO updateContribution(Long id, ContributionDTO contributionDTO) {
        Contribution contribution = contributionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found with id: " + id));
        IContributionMapper.INSTANCE.updateFromDto(contributionDTO, contribution);
        contribution = contributionRepository.save(contribution);

        return IContributionMapper.INSTANCE.toDto(contribution);
    }

    @Override
    public void deleteContribution(Long id) {
        Contribution contribution = contributionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contribution not found with id: " + id));

        contribution.setRemovedAt(LocalDateTime.now());
        contributionRepository.save(contribution);
    }

    @Override
    public void updateStates(Long fileId, Long routeId, List<Long> contributionIds, ContributionStates state) {
        if (!routeRepository.existsById(routeId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + routeId);
        }
        stateChangeService.changeState(contributionIds, state);
    }
}
