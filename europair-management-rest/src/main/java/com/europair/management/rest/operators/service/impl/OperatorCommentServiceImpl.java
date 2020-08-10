package com.europair.management.rest.operators.service.impl;

import com.europair.management.rest.common.exception.ResourceNotFoundException;
import com.europair.management.rest.model.operators.dto.OperatorCommentDTO;
import com.europair.management.rest.model.operators.entity.OperatorComment;
import com.europair.management.rest.model.operators.mapper.IOperatorCommentMapper;
import com.europair.management.rest.operators.repository.IOperatorCommentRepository;
import com.europair.management.rest.operators.repository.IOperatorRepository;
import com.europair.management.rest.operators.service.IOperatorCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OperatorCommentServiceImpl implements IOperatorCommentService {

  final private IOperatorCommentRepository operatorCommentRepository;
  final private IOperatorRepository operatorRepository;

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
  public OperatorCommentDTO saveOperatorComment(OperatorCommentDTO operatorCommentDTO, Long operatorId) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    OperatorComment operatorComment = IOperatorCommentMapper.INSTANCE.toEntity(operatorCommentDTO);
    operatorComment = operatorCommentRepository.save(operatorComment);
    return IOperatorCommentMapper.INSTANCE.toDto(operatorComment);
  }

  @Override
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
  public void deleteOperatorComment(Long id, Long operatorId) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    OperatorComment operatorComment = operatorCommentRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("OperatorComment not found on id: " + id));
    operatorCommentRepository.deleteById(id);
  }

}
