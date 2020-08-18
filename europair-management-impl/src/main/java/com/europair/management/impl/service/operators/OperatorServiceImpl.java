package com.europair.management.impl.service.operators;


import com.europair.management.api.dto.airport.AirportDto;
import com.europair.management.api.dto.operators.OperatorDTO;
import com.europair.management.api.dto.operatorsairports.OperatorsAirportsDTO;
import com.europair.management.impl.common.exception.InvalidArgumentException;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.operators.IOperatorMapper;
import com.europair.management.impl.mappers.operatorsAirports.IOperatorsAirportsMapper;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.operators.entity.Operator;

import com.europair.management.rest.model.operators.repository.IOperatorRepository;
import com.europair.management.rest.model.operators.repository.OperatorSpecification;
import com.europair.management.rest.model.operatorscertifications.entity.OperatorsAirports;
import com.europair.management.rest.model.operatorscertifications.repository.IOperatorsAirportsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OperatorServiceImpl implements IOperatorService {

  // ATTRIBUTES //
  private final IOperatorRepository operatorRepository;
  private final IOperatorsAirportsRepository operatorsAirportsRepository;

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

    operatorRepository.save(operatorBD);

  }

  @Override
  public Page<OperatorDTO> searchOperator(String filter, Pageable pageable) {
    Page<Operator> operatorsWithFilter = operatorRepository.findAll(Specification.where(new OperatorSpecification(filter)), pageable);
    return operatorsWithFilter.map(operator -> IOperatorMapper.INSTANCE.toDto(operator));
  }

  @Override
  public Page<OperatorsAirportsDTO> findOperatorAirportsByOperatorPaginated(Long operatorId, Pageable pageable) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    Page<OperatorsAirports> airportsList = operatorsAirportsRepository.findByOperatorId(operatorId, pageable);

    return airportsList.map(operatorsAirports -> IOperatorsAirportsMapper.INSTANCE.toDto(operatorsAirports));
  }

  @Override
  public OperatorsAirportsDTO saveOperatorsAirports(Long operatorId, OperatorsAirportsDTO operatorsAirportsDTO) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    OperatorsAirports operatorsAirports = IOperatorsAirportsMapper.INSTANCE.toEntity(operatorsAirportsDTO);
    operatorsAirports = operatorsAirportsRepository.save(operatorsAirports);
    return IOperatorsAirportsMapper.INSTANCE.toDto(operatorsAirports);

  }
}
