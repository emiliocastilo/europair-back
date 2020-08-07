package com.europair.management.rest.operators.service;

import com.europair.management.rest.model.operators.dto.OperatorDTO;
import com.europair.management.rest.model.tasks.dto.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OperatorService {

  Page<OperatorDTO> findAllPaginated(Pageable pageable);
  OperatorDTO findById(Long id);
  OperatorDTO saveOperator(OperatorDTO operatorDTO);
  OperatorDTO updateOperator(Long id, OperatorDTO operatorDTO);
  void deleteOperator(Long id);
  Page<OperatorDTO> searchOperator(String filter, Pageable pageable);

}
