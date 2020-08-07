package com.europair.management.rest.operators.service.impl;

import com.europair.management.rest.common.exception.InvalidArgumentException;
import com.europair.management.rest.common.exception.ResourceNotFoundException;
import com.europair.management.rest.model.operators.dto.OperatorDTO;
import com.europair.management.rest.model.operators.entity.Operator;
import com.europair.management.rest.model.operators.mapper.OperatorMapper;
import com.europair.management.rest.operators.repository.OperatorRepository;
import com.europair.management.rest.operators.repository.OperatorSpecification;
import com.europair.management.rest.operators.service.OperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OperatorServiceImpl implements OperatorService {

  private final OperatorRepository operatorRepository;

  @Override
  public Page<OperatorDTO> findAllPaginated(Pageable pageable) {
    return operatorRepository.findAll(pageable).map(operator -> OperatorMapper.INSTANCE.toDto(operator));
  }

  @Override
  public OperatorDTO findById(Long id) throws ResourceNotFoundException {
    return OperatorMapper.INSTANCE.toDto(operatorRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Operator not found on id: " + id)));
  }

  @Override
  public OperatorDTO saveOperator(OperatorDTO operatorDTO) {

    if(operatorDTO.getId() != null){
      throw new InvalidArgumentException(String.format("New operator expected. Identifier %s got", operatorDTO.getId()));
    }
    Operator operator = OperatorMapper.INSTANCE.toEntity(operatorDTO);
    operator = operatorRepository.save(operator);
    return OperatorMapper.INSTANCE.toDto(operator);
  }

  @Override
  public OperatorDTO updateOperator(Long id, OperatorDTO operatorDTO) {

    Operator operator = operatorRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Operator not found on id: " + id));

    OperatorMapper.INSTANCE.updateFromDto(operatorDTO, operator);
    operator = operatorRepository.save(operator);

    return OperatorMapper.INSTANCE.toDto(operator);

  }

  @Override
  public void deleteOperator(Long id) {

    Operator operatorBD = operatorRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Operator not found on id: " + id));

    operatorRepository.save(operatorBD);

  }

  @Override
  public Page<OperatorDTO> searchOperator(String filter, Pageable pageable) {
    Page<Operator> operatorsWithFilter = operatorRepository.findAll(Specification.where(new OperatorSpecification(filter)), pageable);
    return operatorsWithFilter.map(operator -> OperatorMapper.INSTANCE.toDto(operator));
  }

}
