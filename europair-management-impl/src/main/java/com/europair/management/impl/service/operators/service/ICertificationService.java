package com.europair.management.impl.service.operators.service;


import com.europair.management.api.dto.operators.dto.CertificationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICertificationService {

  Page<CertificationDTO> findAllPaginated(Pageable pageable, Long operatorId);
  CertificationDTO findById(Long id, Long operatorId);
  CertificationDTO saveCertification(CertificationDTO certificationDTO, Long operatorId);
  CertificationDTO updateCertification(Long id, CertificationDTO certificationDTO, Long operatorId);
  void deleteCertification(Long id, Long operatorId);

}
