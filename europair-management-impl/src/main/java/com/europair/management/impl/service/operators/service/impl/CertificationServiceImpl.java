package com.europair.management.impl.service.operators.service.impl;


import com.europair.management.api.dto.operators.dto.CertificationDTO;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.operators.ICertificationMapper;
import com.europair.management.impl.service.operators.service.ICertificationService;
import com.europair.management.rest.model.operators.entity.Certification;

import com.europair.management.rest.model.operators.repository.ICertificationRepository;
import com.europair.management.rest.model.operators.repository.IOperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CertificationServiceImpl implements ICertificationService {

  final private ICertificationRepository certificationRepository;
  final private IOperatorRepository operatorRepository;

  @Override
  public Page<CertificationDTO> findAllPaginated(Pageable pageable, Long operatorId) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    return certificationRepository.findAll(pageable).map(certification -> ICertificationMapper.INSTANCE.toDto(certification));
  }

  @Override
  public CertificationDTO findById(Long id, Long operatorId) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    return ICertificationMapper.INSTANCE.toDto(certificationRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Certification not found on id: " + id)));
  }

  @Override
  public CertificationDTO saveCertification(CertificationDTO certificationDTO, Long operatorId) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    Certification certification = ICertificationMapper.INSTANCE.toEntity(certificationDTO);
    certification = certificationRepository.save(certification);
    return ICertificationMapper.INSTANCE.toDto(certification);
  }

  @Override
  public CertificationDTO updateCertification(Long id, CertificationDTO certificationDTO, Long operatorId) {

    if (operatorRepository.findById(operatorId).isEmpty()) {
      throw new ResourceNotFoundException("Operator not found on id: " + operatorId);
    }

    Certification certification = certificationRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Certification not found on id: " + id));

    ICertificationMapper.INSTANCE.updateFromDto(certificationDTO, certification);
    certification = certificationRepository.save(certification);

    return ICertificationMapper.INSTANCE.toDto(certification);

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

}
