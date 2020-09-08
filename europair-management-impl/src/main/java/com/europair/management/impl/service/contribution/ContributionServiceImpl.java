package com.europair.management.impl.service.contribution;

import com.europair.management.api.dto.contribution.ContributionDTO;
import com.europair.management.impl.common.exception.InvalidArgumentException;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.contributions.IContributionMapper;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.contributions.repository.ContributionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;

@Service
@Transactional
public class ContributionServiceImpl implements IContributionService {

    @Autowired
    private ContributionRepository contributionRepository;

    @Override
    public Page<ContributionDTO> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return contributionRepository.findContributionByCriteria(criteria, pageable).map(IContributionMapper.INSTANCE::toDto);

    }

    @Override
    public ContributionDTO findById(Long id) {
        return IContributionMapper.INSTANCE.toDto(contributionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contribution not found with id: " + id)));
    }

    @Override
    public ContributionDTO saveContribution(ContributionDTO contributionDTO) {
        if (contributionDTO.getId() != null) {
            throw new InvalidArgumentException(String.format("New Contribution expected. Identifier %s got", contributionDTO.getId()));
        }

        Contribution contribution = IContributionMapper.INSTANCE.toEntity(contributionDTO);
        contribution = contributionRepository.save(contribution);

        return IContributionMapper.INSTANCE.toDto(contribution);
    }

    @Override
    public ContributionDTO updateContribution(Long id, ContributionDTO contributionDTO) {
        Contribution contribution = contributionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contribution not found with id: " + id));
        IContributionMapper.INSTANCE.updateFromDto(contributionDTO, contribution);
        contribution = contributionRepository.save(contribution);

        return IContributionMapper.INSTANCE.toDto(contribution);
    }

    @Override
    public void deleteContribution(Long id) {
        Contribution contribution = contributionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contribution not found with id: " + id));

        contribution.setRemovedAt(LocalDate.now());
        contributionRepository.save(contribution);
    }
}
