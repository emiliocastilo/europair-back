package com.europair.management.impl.service.operators;


import com.europair.management.api.dto.operators.OperatorCommentDTO;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.operators.IOperatorCommentMapper;
import com.europair.management.rest.model.operators.entity.Operator;
import com.europair.management.rest.model.operators.entity.OperatorComment;

import com.europair.management.rest.model.operators.repository.IOperatorCommentRepository;
import com.europair.management.rest.model.operators.repository.OperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OperatorCommentServiceImpl implements IOperatorCommentService {

  final private IOperatorCommentRepository operatorCommentRepository;
  final private OperatorRepository operatorRepository;

  @Override
  public Page<OperatorCommentDTO> findAllPaginated(Pageable pageable, Long operatorId) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    return operatorCommentRepository.findAll(pageable).map(comment -> IOperatorCommentMapper.INSTANCE.toDto(comment));
  }

  @Override
  public OperatorCommentDTO findById(Long id, Long operatorId) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    return IOperatorCommentMapper.INSTANCE.toDto(operatorCommentRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("OperatorComment not found on id: " + id)));
  }

  @Override
  @Transactional(readOnly = false)
  public OperatorCommentDTO saveOperatorComment(OperatorCommentDTO operatorCommentDTO, Long operatorId) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    OperatorComment operatorComment = IOperatorCommentMapper.INSTANCE.toEntity(operatorCommentDTO);

    Operator operator = new Operator();
    operator.setId(operatorId);
    operatorComment.setOperator(operator);

    operatorComment = operatorCommentRepository.save(operatorComment);
    return IOperatorCommentMapper.INSTANCE.toDto(operatorComment);
  }

  @Override
  @Transactional(readOnly = false)
  public OperatorCommentDTO updateOperatorComment(Long id, OperatorCommentDTO operatorCommentDTO, Long operatorId) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    OperatorComment operatorComment = operatorCommentRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("OperatorComment not found on id: " + id));

    IOperatorCommentMapper.INSTANCE.updateFromDto(operatorCommentDTO, operatorComment);
    operatorComment = operatorCommentRepository.save(operatorComment);

    return IOperatorCommentMapper.INSTANCE.toDto(operatorComment);

  }

  @Override
  @Transactional(readOnly = false)
  public void deleteOperatorComment(Long id, Long operatorId) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    OperatorComment operatorComment = operatorCommentRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("OperatorComment not found on id: " + id));
    operatorCommentRepository.deleteById(id);
  }

}
