package com.europair.management.impl.service.contribution;

import com.europair.management.api.dto.common.StateChangeDto;
import com.europair.management.api.dto.contribution.ContributionDTO;
import com.europair.management.api.dto.contribution.LineContributionRouteDTO;
import com.europair.management.api.enums.ContributionStatesEnum;
import com.europair.management.api.service.contribution.IContributionController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class ContributionController implements IContributionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContributionController.class);

    @Autowired
    private IContributionService contributionService;

    @Override
    public ResponseEntity<ContributionDTO> getContributionById(@NotNull Long id) {
        LOGGER.debug("[ContributionController] - Starting method [getContributionById] with input: id={}", id);
        ContributionDTO dto = contributionService.findById(id);
        LOGGER.debug("[ContributionController] - Ending method [getContributionById] with return: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Page<ContributionDTO>> getContributionByFilter(Long routeId, Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[ContributionController] - Starting method [getContributionByFilter] with input: pageable={}, repParam={}", pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<ContributionDTO> dtoPage = contributionService.findAllPaginatedByFilter(routeId, pageable, criteria);
        LOGGER.debug("[ContributionController] - Ending method [getContributionByFilter] with return: {}", dtoPage);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<Page<LineContributionRouteDTO>> getLineContributionRouteByFilter(Pageable pageable, @NotNull Long contributionId, Map<String, String> reqParam) {
        LOGGER.debug("[ContributionController] - Starting method [getLineContributionRouteByFilter] with input: pageable={}, contributionId={}, repParam={}",
                pageable, contributionId, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<LineContributionRouteDTO> dtoPage = contributionService.findAllPaginatedLineContributionRouteByFilter(contributionId, pageable, criteria);
        LOGGER.debug("[ContributionController] - Ending method [getLineContributionRouteByFilter] with return: {}", dtoPage);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<ContributionDTO> saveContribution(@NotNull ContributionDTO contributionDTO) {
        LOGGER.debug("[ContributionController] - Starting method [saveContribution] with input: contributionDTO={}", contributionDTO);
        final ContributionDTO contributionDTOSaved = contributionService.saveContribution(contributionDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(contributionDTOSaved.getId())
                .toUri();

        LOGGER.debug("[ContributionController] - Ending method [saveContribution] with return: {}", contributionDTOSaved);
        return ResponseEntity.created(location).body(contributionDTOSaved);
    }

    @Override
    public ResponseEntity<Long> saveLineContributionRoute(
            @NotNull final Long fileId,
            @NotNull final Long routeId,
            @NotNull final Long contributionId,
            @NotNull final LineContributionRouteDTO lineContributionRouteDTO) {
        LOGGER.debug("[ContributionController] - Starting method [saveLineContributionRoute] with input: fileId={}, routeId={}, contributionId={}, lineContributionRouteDTO={}",
                fileId, routeId, contributionId, lineContributionRouteDTO);

        if (routeId.equals(lineContributionRouteDTO.getRouteId()) && contributionId.equals(lineContributionRouteDTO.getContributionId())) {

            final Long lineContributionRouteSavedId = this.contributionService.saveLineContributionRoute(lineContributionRouteDTO);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(lineContributionRouteSavedId)
                    .toUri();

            LOGGER.debug("[ContributionController] - Ending method [saveLineContributionRoute] with return: {}", lineContributionRouteSavedId);
            return ResponseEntity.created(location).body(lineContributionRouteSavedId);

        } else {
            LOGGER.debug("[ContributionController] - Ending method [saveLineContributionRoute] with unprocessableEntity return.");
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @Override
    public ResponseEntity<ContributionDTO> updateContribution(@NotNull Long id, @NotNull ContributionDTO contributionDTO) {
        LOGGER.debug("[ContributionController] - Starting method [updateContribution] with input: id={}, contributionDTO={}", id, contributionDTO);
        final ContributionDTO dtoSaved = contributionService.updateContribution(id, contributionDTO);
        LOGGER.debug("[ContributionController] - Ending method [updateContribution] with return: {}", dtoSaved);
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
        LOGGER.debug("[ContributionController] - Starting method [updateLineContributionRoute] with input: fileId={}, routeId={}, contributionId={}, lineContributionRouteId={}, lineContributionRouteDTO={}",
                fileId, routeId, contributionId, lineContributionRouteId, lineContributionRouteDTO);

        if (validateParametersOperationUpdate(contributionId, lineContributionRouteId, lineContributionRouteDTO)) {

            final Boolean res = contributionService.updateLineContributionRoute(routeId, contributionId, lineContributionRouteId, lineContributionRouteDTO);
            LOGGER.debug("[ContributionController] - Ending method [updateLineContributionRoute] with return: {}", Boolean.TRUE.equals(res) ? "Ok" : "NotAcceptable");
            return (res ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build());

        } else {
            LOGGER.debug("[ContributionController] - Ending method [updateLineContributionRoute] with unprocessableEntity return.");
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    private boolean validateParametersOperationUpdate(Long contributionId, Long lineContributionRouteId, LineContributionRouteDTO lineContributionRouteDTO) {
        return contributionId.equals(lineContributionRouteDTO.getContributionId())
                && lineContributionRouteId.equals(lineContributionRouteDTO.getId());
    }


    @Override
    public ResponseEntity<?> deleteContribution(@NotNull Long id) {
        LOGGER.debug("[ContributionController] - Starting method [deleteContribution] with input: id={}", id);
        contributionService.deleteContribution(id);
        LOGGER.debug("[ContributionController] - Ending method [deleteContribution] with no return.");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> deleteLineContributionRoute(
            @NotNull final Long fileId,
            @NotNull final Long routeId,
            @NotNull Long contributionId,
            @NotNull Long lineContributionRouteId) {
        LOGGER.debug("[ContributionController] - Starting method [deleteLineContributionRoute] with input: fileId={}, routeId={}, contributionId={}, lineContributionRouteId={}",
                fileId, routeId, contributionId, lineContributionRouteId);

        this.contributionService.deleteLineContributionRoute(contributionId, lineContributionRouteId);
        LOGGER.debug("[ContributionController] - Ending method [deleteLineContributionRoute] with no return.");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> changeState(@NotNull Long fileId, @NotNull Long routeId, @NotNull StateChangeDto<ContributionStatesEnum> stateChangeDto) {
        LOGGER.debug("[ContributionController] - Starting method [changeState] with input: fileId={}, routeId={}, stateChangeDto={}",
                fileId, routeId, stateChangeDto);
        contributionService.updateStates(fileId, routeId, stateChangeDto.getIdList(), stateChangeDto.getState());
        LOGGER.debug("[ContributionController] - Ending method [changeState] with no return.");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<String>> getValidContributionStatesToChange(@NotNull Long fileId, @NotNull Long routeId, @NotNull Long id) {
        LOGGER.debug("[ContributionController] - Starting method [getValidContributionStatesToChange] with input: fileId={}, routeId={}, id={}",
                fileId, routeId, id);
        List<String> res = contributionService.getValidContributionStatesToChange(fileId, routeId, id);
        LOGGER.debug("[ContributionController] - Ending method [getValidContributionStatesToChange] with no return.");
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<?> generateRouteContributionSaleLines(@NotNull Long contributionId) {
        LOGGER.debug("[ContributionController] - Starting method [generateRouteContributionSaleLines] with input: contributionId={}", contributionId);
        contributionService.generateRouteContributionSaleLines(contributionId);
        LOGGER.debug("[ContributionController] - Ending method [generateRouteContributionSaleLines] with no return.");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<LineContributionRouteDTO> getLineContributionRouteById(@NotNull Long fileId, @NotNull Long routeId, @NotNull Long contributionId, @NotNull Long lineId) {
        LineContributionRouteDTO dto = contributionService.findLineById(fileId, routeId, contributionId, lineId);
        return ResponseEntity.ok(dto);
    }
}
