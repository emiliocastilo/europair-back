package com.europair.management.impl.service.operators.service;


import com.europair.management.api.dto.operators.dto.OperatorCommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOperatorCommentService {

  Page<OperatorCommentDTO> findAllPaginated(Pageable pageable, Long operatorCommentId);
  OperatorCommentDTO findById(Long id, Long operatorId);
  OperatorCommentDTO saveOperatorComment(OperatorCommentDTO operatorCommentDTO, Long operatorId);
  OperatorCommentDTO updateOperatorComment(Long id, OperatorCommentDTO operatorCommentDTO, Long operatorId);
  void deleteOperatorComment(Long id, Long operatorId);

}
