package com.europair.management.rest.fleet.service.impl;

import com.europair.management.rest.common.exception.ResourceNotFoundException;
import com.europair.management.rest.fleet.repository.AircraftRepository;
import com.europair.management.rest.fleet.service.AircraftService;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.fleet.dto.AircraftDto;
import com.europair.management.rest.model.fleet.mapper.AircraftMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AircraftServiceImpl implements AircraftService {

    @Autowired
    private AircraftRepository aircraftRepository;

    @Override
    public Page<AircraftDto> findAllPaginated(Pageable pageable) {
        return aircraftRepository.findAll(pageable).map(AircraftMapper.INSTANCE::toDto);
    }

    @Override
    public AircraftDto findById(Long id) {
        return AircraftMapper.INSTANCE.toDto(aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found with id: " + id)));
    }

    @Override
    public Page<AircraftDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return aircraftRepository.findAircraftsByCriteria(criteria, pageable)
                .map(AircraftMapper.INSTANCE::toDto);
    }

    @Override
    public Page<AircraftDto> findAllPaginatedByBasicFilter(Pageable pageable, String filter) {
        return aircraftRepository.findByBasicFilter(pageable, filter)
                .map(AircraftMapper.INSTANCE::toDto);
    }

}
