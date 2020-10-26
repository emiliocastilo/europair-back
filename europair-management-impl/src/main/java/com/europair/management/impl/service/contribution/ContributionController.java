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
    public ResponseEntity<Page<ContributionDTO>> getContributionByFilter(Long routeId, Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<ContributionDTO> dtoPage = contributionService.findAllPaginatedByFilter(routeId, pageable, criteria);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<Page<LineContributionRouteDTO>> getLineContributionRouteByFilter(Pageable pageable, @NotNull Long contributionId, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<LineContributionRouteDTO> dtoPage = contributionService.findAllPaginatedLineContributionRouteByFilter(contributionId, pageable, criteria);
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
    public ResponseEntity<Long> saveLineContributionRoute(
            @NotNull final Long fileId,
            @NotNull final Long routeId,
            @NotNull final Long contributionId,
            @NotNull final LineContributionRouteDTO lineContributionRouteDTO) {

        if (routeId.equals(lineContributionRouteDTO.getRouteId()) && contributionId.equals(lineContributionRouteDTO.getContributionId())) {

            final Long lineContributionRouteSavedId = this.contributionService.saveLineContributionRoute(lineContributionRouteDTO);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(lineContributionRouteSavedId)
                    .toUri();

            return ResponseEntity.created(location).body(lineContributionRouteSavedId);

        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
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
    public ResponseEntity<HttpStatus> updateLineContributionRoute(
            @NotNull final Long fileId,
            @NotNull final Long routeId,
            @NotNull Long contributionId,
            @NotNull Long lineContributionRouteId,
            @NotNull LineContributionRouteDTO lineContributionRouteDTO) {

        if (validateParametersOperationUpdate(contributionId, lineContributionRouteId, lineContributionRouteDTO)) {

            final Boolean res = contributionService.updateLineContributionRoute(routeId, contributionId, lineContributionRouteId, lineContributionRouteDTO);
            return (res ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build());

        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    private boolean validateParametersOperationUpdate(Long contributionId, Long lineContributionRouteId, LineContributionRouteDTO lineContributionRouteDTO) {
        return contributionId.equals(lineContributionRouteDTO.getContributionId())
                && lineContributionRouteId.equals(lineContributionRouteDTO.getId());
    }


    @Override
    public ResponseEntity<?> deleteContribution(@NotNull Long id) {
        contributionService.deleteContribution(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> deleteLineContributionRoute(
            @NotNull final Long fileId,
            @NotNull final Long routeId,
            @NotNull Long contributionId,
            @NotNull Long lineContributionRouteId) {

        this.contributionService.deleteLineContributionRoute(contributionId, lineContributionRouteId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> changeState(@NotNull Long fileId, @NotNull Long routeId, @NotNull StateChangeDto<ContributionStatesEnum> stateChangeDto) {
        contributionService.updateStates(fileId, routeId, stateChangeDto.getIdList(), stateChangeDto.getState());
        return ResponseEntity.noContent().build();
    }


    @Override
    public ResponseEntity<?> generateRouteContributionSaleLines(@NotNull Long contributionId) {
        contributionService.generateRouteContributionSaleLines(contributionId);
        return ResponseEntity.noContent().build();
    }
}
