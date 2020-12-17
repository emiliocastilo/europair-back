package com.europair.management.impl.service.operators;

import com.europair.management.api.dto.operators.OperatorDTO;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.mappers.operators.IOperatorMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.operators.entity.Operator;
import com.europair.management.rest.model.operators.repository.OperatorRepository;
import com.europair.management.rest.model.operators.repository.OperatorSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OperatorServiceImpl implements IOperatorService {

  // ATTRIBUTES //
  private final OperatorRepository operatorRepository;

  // REST METHODS //
  @Override
  public Page<OperatorDTO> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
    return operatorRepository.findOperatorByCriteria(criteria, pageable).map(operator -> IOperatorMapper.INSTANCE.toDto(operator));
  }

  @Override
  public OperatorDTO findById(final Long id) {
    return IOperatorMapper.INSTANCE.toDto(operatorRepository.findById(id)
        .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.OPERATOR_NOT_FOUND, String.valueOf(id))));
  }

  @Transactional(readOnly = false)
  @Override
  public OperatorDTO saveOperator(final OperatorDTO operatorDTO) {

    if (operatorDTO.getId() != null) {
      throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.OPERATOR_NEW_WITH_ID, String.valueOf(operatorDTO.getId()));
    }

    Operator operator = IOperatorMapper.INSTANCE.toEntity(operatorDTO);
    operator = operatorRepository.save(operator);
    return IOperatorMapper.INSTANCE.toDto(operator);
  }

  @Transactional(readOnly = false)
  @Override
  public OperatorDTO updateOperator(Long id, final OperatorDTO operatorDTO) {

    Operator operator = operatorRepository.findById(id)
        .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.OPERATOR_NOT_FOUND, String.valueOf(id)));

    IOperatorMapper.INSTANCE.updateFromDto(operatorDTO, operator);
    operator = operatorRepository.save(operator);
    return IOperatorMapper.INSTANCE.toDto(operator);
  }

  @Transactional(readOnly = false)
  @Override
  public void deleteOperator(Long id) {

    Operator operator = operatorRepository.findById(id)
        .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.OPERATOR_NOT_FOUND, String.valueOf(id)));

    operator.setRemovedAt(LocalDateTime.now());
    operatorRepository.save(operator);

  }

  @Override
  public Page<OperatorDTO> searchOperator(String filter, Pageable pageable) {
    Page<Operator> operatorsWithFilter = operatorRepository.findAll((Specification.where(new OperatorSpecification(filter))), pageable);
    return operatorsWithFilter.map(operator -> IOperatorMapper.INSTANCE.toDto(operator));
  }

  @Override
  public void reactivateOperators(@NotEmpty Set<Long> operatorIds) {
    Set<Operator> operators = operatorRepository.findByIdIn(operatorIds).stream()
            .map(operator -> {
              operator.setRemovedAt(null);
              return operator;
            })
            .collect(Collectors.toSet());
    operators = new HashSet<>(operatorRepository.saveAll(operators));
  }
}
