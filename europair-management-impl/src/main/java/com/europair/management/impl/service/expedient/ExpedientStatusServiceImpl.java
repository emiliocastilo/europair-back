package com.europair.management.impl.service.expedient;


import com.europair.management.api.dto.expedient.ExpedientStatusDto;
import com.europair.management.impl.common.exception.InvalidArgumentException;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.expedient.IExpedientStatusMapper;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.expedient.entity.ExpedientStatus;
import com.europair.management.rest.model.expedient.repository.ExpedientStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExpedientStatusServiceImpl implements IExpedientStatusService {

    @Autowired
    private ExpedientStatusRepository expedientStatusRepository;

    @Override
    public ExpedientStatusDto findById(Long id) {
        return IExpedientStatusMapper.INSTANCE.toDto(expedientStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExpedientStatus not found with id: " + id)));
    }

    @Override
    public Page<ExpedientStatusDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return expedientStatusRepository.findExpedientStatusByCriteria(criteria, pageable)
                .map(IExpedientStatusMapper.INSTANCE::toDto);
    }

    @Override
    public ExpedientStatusDto saveExpedientStatus(final ExpedientStatusDto expedientStatusDto) {

        if (expedientStatusDto.getId() != null) {
            throw new InvalidArgumentException(String.format("New expedientStatus expected. Identifier %s got", expedientStatusDto.getId()));
        }
        ExpedientStatus expedientStatus = IExpedientStatusMapper.INSTANCE.toEntity(expedientStatusDto);
        expedientStatus = expedientStatusRepository.save(expedientStatus);

        return IExpedientStatusMapper.INSTANCE.toDto(expedientStatus);
    }

    @Override
    public ExpedientStatusDto updateExpedientStatus(Long id, ExpedientStatusDto expedientStatusDto) {
        ExpedientStatus expedientStatus = expedientStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExpedientStatus not found with id: " + id));

        IExpedientStatusMapper.INSTANCE.updateFromDto(expedientStatusDto, expedientStatus);
        expedientStatus = expedientStatusRepository.save(expedientStatus);

        return IExpedientStatusMapper.INSTANCE.toDto(expedientStatus);
    }

    @Override
    public void deleteExpedientStatus(Long id) {
        if (!expedientStatusRepository.existsById(id)) {
            throw new ResourceNotFoundException("ExpedientStatus not found with id: " + id);
        }
        expedientStatusRepository.deleteById(id);
    }
}
