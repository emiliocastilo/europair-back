package com.europair.management.impl.service.operators;


import com.europair.management.api.dto.operators.OperatorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOperatorService {

  Page<OperatorDTO> findAllPaginated(Pageable pageable);
  OperatorDTO findById(Long id);
  OperatorDTO saveOperator(OperatorDTO operatorDTO);
  OperatorDTO updateOperator(Long id, OperatorDTO operatorDTO);
  void deleteOperator(Long id);
  Page<OperatorDTO> searchOperator(String filter, Pageable pageable);

}
