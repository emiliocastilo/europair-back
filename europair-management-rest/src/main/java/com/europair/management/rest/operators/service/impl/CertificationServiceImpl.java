package com.europair.management.rest.operators.service.impl;

import com.europair.management.rest.common.exception.ResourceNotFoundException;
import com.europair.management.rest.model.operators.dto.CertificationDTO;
import com.europair.management.rest.model.operators.dto.OperatorDTO;
import com.europair.management.rest.model.operators.entity.Certification;
import com.europair.management.rest.model.operators.entity.Operator;
import com.europair.management.rest.model.operators.mapper.CertificationMapper;
import com.europair.management.rest.model.operators.mapper.OperatorMapper;
import com.europair.management.rest.operators.repository.CertificationRepository;
import com.europair.management.rest.operators.repository.OperatorRepository;
import com.europair.management.rest.operators.service.CertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {

  final private CertificationRepository certificationRepository;
  final private OperatorRepository operatorRepository;

  @Override
  public Page<CertificationDTO> findAllPaginated(Pageable pageable, Long operatorId) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    return certificationRepository.findAll(pageable).map(certification -> CertificationMapper.INSTANCE.toDto(certification));
  }

  @Override
  public CertificationDTO findById(Long id, Long operatorId) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    return CertificationMapper.INSTANCE.toDto(certificationRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Certification not found on id: " + id)));
  }

  @Override
  public CertificationDTO saveCertification(CertificationDTO certificationDTO, Long operatorId) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    Certification certification = CertificationMapper.INSTANCE.toEntity(certificationDTO);
    certification = certificationRepository.save(certification);
    return CertificationMapper.INSTANCE.toDto(certification);
  }

  @Override
  public CertificationDTO updateCertification(Long id, CertificationDTO certificationDTO, Long operatorId) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    Certification certificationBD = certificationRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Certification not found on id: " + id));

    CertificationDTO certificationDTO2Update = updateCertificationValues(certificationDTO);

    Certification certification = CertificationMapper.INSTANCE.toEntity(certificationDTO2Update);
    certification = certificationRepository.save(certification);

    return CertificationMapper.INSTANCE.toDto(certification);

  }

  @Override
  public void deleteCertification(Long id, Long operatorId) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    Certification certification = certificationRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Certification not found on id: " + id));
    certificationRepository.deleteById(id);
  }


  private CertificationDTO updateCertificationValues(CertificationDTO certificationDTO) {

    return CertificationDTO.builder()
      .id(certificationDTO.getId())
      .airport(certificationDTO.getAirport())
      .comment(certificationDTO.getComment())
      .operator(certificationDTO.getOperator())
      .build();

  }
}
