package com.europair.management.rest.operators.service;

import com.europair.management.rest.model.operators.dto.CertificationDTO;
import com.europair.management.rest.model.operators.dto.OperatorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CertificationService {

  Page<CertificationDTO> findAllPaginated(Pageable pageable, Long operatorId);
  CertificationDTO findById(Long id, Long operatorId);
  CertificationDTO saveCertification(CertificationDTO certificationDTO, Long operatorId);
  CertificationDTO updateCertification(Long id, CertificationDTO certificationDTO, Long operatorId);
  void deleteCertification(Long id, Long operatorId);

}
