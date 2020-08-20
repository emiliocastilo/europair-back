package com.europair.management.impl.service.operators;


import com.europair.management.api.dto.operators.OperatorDTO;
import com.europair.management.api.dto.operatorsairports.OperatorsAirportsDTO;
import com.europair.management.impl.common.exception.InvalidArgumentException;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.operators.IOperatorMapper;
import com.europair.management.impl.mappers.operatorsAirports.IOperatorsAirportsMapper;
import com.europair.management.rest.model.operators.entity.Operator;

import com.europair.management.rest.model.operators.repository.IOperatorRepository;
import com.europair.management.rest.model.operators.repository.OperatorSpecification;
import com.europair.management.rest.model.operatorsairports.entity.OperatorsAirports;
import com.europair.management.rest.model.operatorsairports.repository.IOperatorsAirportsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OperatorServiceImpl implements IOperatorService {

  // ATTRIBUTES //
  private final IOperatorRepository operatorRepository;

  // REST METHODS //
  @Override
  public Page<OperatorDTO> findAllPaginated(Pageable pageable) {
    return operatorRepository.findAll(pageable).map(operator -> IOperatorMapper.INSTANCE.toDto(operator));
  }

  @Override
  public OperatorDTO findById(final Long id) throws ResourceNotFoundException {
    return IOperatorMapper.INSTANCE.toDto(operatorRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Operator not found on id: " + id)));
  }

  @Transactional(readOnly = false)
  @Override
  public OperatorDTO saveOperator(final OperatorDTO operatorDTO) {

    if (operatorDTO.getId() != null) {
      throw new InvalidArgumentException(String.format("New operator expected. Identifier %s got", operatorDTO.getId()));
    }
    Operator operator = IOperatorMapper.INSTANCE.toEntity(operatorDTO);
    operator = operatorRepository.save(operator);
    return IOperatorMapper.INSTANCE.toDto(operator);
  }

  @Transactional(readOnly = false)
  @Override
  public OperatorDTO updateOperator(Long id, final OperatorDTO operatorDTO) {

    Operator operator = operatorRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Operator not found on id: " + id));

    IOperatorMapper.INSTANCE.updateFromDto(operatorDTO, operator);
    operator = operatorRepository.save(operator);
    return IOperatorMapper.INSTANCE.toDto(operator);

  }

  @Override
  public void deleteOperator(Long id) {

    Operator operatorBD = operatorRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Operator not found on id: " + id));

    operatorBD.setRemovedAt(new Date());
    operatorRepository.save(operatorBD);

  }

  @Override
  public Page<OperatorDTO> searchOperator(String filter, Pageable pageable) {
    Page<Operator> operatorsWithFilter = operatorRepository.findAll(Specification.where(new OperatorSpecification(filter)), pageable);
    return operatorsWithFilter.map(operator -> IOperatorMapper.INSTANCE.toDto(operator));
  }

}
