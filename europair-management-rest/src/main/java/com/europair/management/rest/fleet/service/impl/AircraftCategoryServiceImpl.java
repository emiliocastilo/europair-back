package com.europair.management.rest.fleet.service.impl;

import com.europair.management.rest.common.exception.ResourceNotFoundException;
import com.europair.management.rest.fleet.repository.AircraftCategoryRepository;
import com.europair.management.rest.fleet.service.AircraftCategoryService;
import com.europair.management.rest.model.fleet.dto.AircraftCategoryDto;
import com.europair.management.rest.model.fleet.mapper.AircraftCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AircraftCategoryServiceImpl implements AircraftCategoryService {

    @Autowired
    private AircraftCategoryRepository aircraftCategoryRepository;

    @Override
    public AircraftCategoryDto saveAircraftCategory(AircraftCategoryDto aircraftCategoryDto) {
        return null;
    }

    @Override
    public AircraftCategoryDto updateAircraftCategory(Long id, AircraftCategoryDto aircraftCategoryDto) {
        return null;
    }

    @Override
    public void deleteAircraftCategory(Long id) {

    }

    @Override
    public AircraftCategoryDto findById(Long id) {
        return AircraftCategoryMapper.INSTANCE.toDtoWithSubcategories(aircraftCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft Category with id: " + id)));
    }

    @Override
    public Page<AircraftCategoryDto> findAllPaginated(Pageable pageable) {
        return aircraftCategoryRepository.findAll(pageable).map(AircraftCategoryMapper.INSTANCE::toDtoWithSubcategories);
    }
}
