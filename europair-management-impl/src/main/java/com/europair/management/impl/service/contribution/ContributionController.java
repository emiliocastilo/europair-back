package com.europair.management.impl.service.contribution;

import com.europair.management.api.dto.common.StateChangeDto;
import com.europair.management.api.dto.contribution.ContributionDTO;
import com.europair.management.api.dto.contribution.LineContributionRouteDTO;
import com.europair.management.api.enums.ContributionStatesEnum;
import com.europair.management.api.service.contribution.IContributionController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Map;

@RestController
@Slf4j
public class ContributionController implements IContributionController {

    @Autowired
    private IContributionService contributionService;

    @Override
    public ResponseEntity<ContributionDTO> getContributionById(@NotNull Long id) {
        ContributionDTO dto = contributionService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<ContributionDTO>> getContributionByFilter(Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<ContributionDTO> dtoPage = contributionService.findAllPaginatedByFilter(pageable, criteria);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<ContributionDTO> saveContribution(@NotNull ContributionDTO contributionDTO) {
        final ContributionDTO contributionDTOSaved = contributionService.saveContribution(contributionDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(contributionDTOSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(contributionDTOSaved);
    }

    @Override
    public ResponseEntity<Long> saveLineContributionRoute(@NotNull LineContributionRouteDTO lineContributionRouteDTO) {

        final Long lineContributionRouteSavedId = this.contributionService.saveLineContributionRoute(lineContributionRouteDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(lineContributionRouteSavedId)
                .toUri();

        return ResponseEntity.created(location).body(lineContributionRouteSavedId);
    }

    @Override
    public ResponseEntity<ContributionDTO> updateContribution(@NotNull Long id, @NotNull ContributionDTO contributionDTO) {
        final ContributionDTO dtoSaved = contributionService.updateContribution(id, contributionDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoSaved.getId())
                .toUri();

        return ResponseEntity.ok(dtoSaved);
    }

    /**
     * <p>
     * Update amount of a LineContributionRoute
     * </p>
     *
     * @param contributionId           Unique identifier
     * @param lineContributionRouteId  Unique identifier
     * @param lineContributionRouteDTO
     * @return
     */
    @Override
    public ResponseEntity<HttpStatus> updateLineContributionRoute(@NotNull Long contributionId, @NotNull Long lineContributionRouteId, LineContributionRouteDTO lineContributionRouteDTO) {
        final Boolean res = contributionService.updateLineContributionRoute(contributionId, lineContributionRouteId, lineContributionRouteDTO);
        return (res ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build());
    }

    @Override
    public ResponseEntity<?> deleteContribution(@NotNull Long id) {
        contributionService.deleteContribution(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> deleteLineContributionRoute(@NotNull Long contributionId, @NotNull Long lineContributionRouteId) {
        this.contributionService.deleteLineContributionRoute(contributionId, lineContributionRouteId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> changeState(@NotNull Long fileId, @NotNull Long routeId, @NotNull StateChangeDto<ContributionStatesEnum> stateChangeDto) {
        contributionService.updateStates(fileId, routeId, stateChangeDto.getIdList(), stateChangeDto.getState());
        return ResponseEntity.noContent().build();
    }

}
