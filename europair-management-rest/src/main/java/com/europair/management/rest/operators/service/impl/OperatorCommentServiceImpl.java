package com.europair.management.rest.operators.service.impl;

import com.europair.management.rest.common.exception.ResourceNotFoundException;
import com.europair.management.rest.model.operators.dto.OperatorCommentDTO;
import com.europair.management.rest.model.operators.entity.OperatorComment;
import com.europair.management.rest.model.operators.mapper.OperatorCommentMapper;
import com.europair.management.rest.operators.repository.OperatorCommentRepository;
import com.europair.management.rest.operators.repository.OperatorRepository;
import com.europair.management.rest.operators.service.OperatorCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OperatorCommentServiceImpl implements OperatorCommentService {

  final private OperatorCommentRepository operatorCommentRepository;
  final private OperatorRepository operatorRepository;

  @Override
  public Page<OperatorCommentDTO> findAllPaginated(Pageable pageable, Long operatorId) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    return operatorCommentRepository.findAll(pageable).map(comment -> OperatorCommentMapper.INSTANCE.toDto(comment));
  }

  @Override
  public OperatorCommentDTO findById(Long id, Long operatorId) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    return OperatorCommentMapper.INSTANCE.toDto(operatorCommentRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("OperatorComment not found on id: " + id)));
  }

  @Override
  public OperatorCommentDTO saveOperatorComment(OperatorCommentDTO operatorCommentDTO, Long operatorId) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    OperatorComment operatorComment = OperatorCommentMapper.INSTANCE.toEntity(operatorCommentDTO);
    operatorComment = operatorCommentRepository.save(operatorComment);
    return OperatorCommentMapper.INSTANCE.toDto(operatorComment);
  }

  @Override
  public OperatorCommentDTO updateOperatorComment(Long id, OperatorCommentDTO operatorCommentDTO, Long operatorId) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    OperatorComment operatorCommentBD = operatorCommentRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Certification not found on id: " + id));

    OperatorCommentDTO operatorCommentDTO2Update = updateOperatorCommentValues(operatorCommentDTO);

    OperatorComment operatorComment = OperatorCommentMapper.INSTANCE.toEntity(operatorCommentDTO2Update);
    operatorComment = operatorCommentRepository.save(operatorComment);

    return OperatorCommentMapper.INSTANCE.toDto(operatorComment);

  }

  @Override
  public void deleteOperatorComment(Long id, Long operatorId) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    OperatorComment operatorComment = operatorCommentRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("OperatorComment not found on id: " + id));
    operatorCommentRepository.deleteById(id);
  }


  private OperatorCommentDTO updateOperatorCommentValues(OperatorCommentDTO operatorCommentDTO) {

    return OperatorCommentDTO.builder()
      .id(operatorCommentDTO.getId())
      .comment(operatorCommentDTO.getComment())
      .operator(operatorCommentDTO.getOperator())
      .build();

  }
}
