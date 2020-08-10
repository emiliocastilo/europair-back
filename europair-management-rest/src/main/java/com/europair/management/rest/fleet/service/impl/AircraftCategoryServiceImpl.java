package com.europair.management.rest.fleet.service.impl;

import com.europair.management.rest.common.exception.InvalidArgumentException;
import com.europair.management.rest.common.exception.ResourceNotFoundException;
import com.europair.management.rest.fleet.repository.AircraftCategoryRepository;
import com.europair.management.rest.fleet.service.AircraftCategoryService;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.fleet.dto.AircraftCategoryDto;
import com.europair.management.rest.model.fleet.entity.AircraftCategory;
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
        if (aircraftCategoryDto.getId() != null) {
            throw new InvalidArgumentException(String.format("New aircraft category expected. Identifier %s got",
                    aircraftCategoryDto.getId()));
        }

        AircraftCategory aircraftCategory = AircraftCategoryMapper.INSTANCE.toEntity(aircraftCategoryDto);

        aircraftCategory = aircraftCategoryRepository.save(aircraftCategory);
        return AircraftCategoryMapper.INSTANCE.toDtoWithSubcategories(aircraftCategory);
    }

    @Override
    public AircraftCategoryDto updateAircraftCategory(Long id, AircraftCategoryDto aircraftCategoryDto) {
        AircraftCategory aircraftCategory = aircraftCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft Category not found with id: " + id));

        AircraftCategoryMapper.INSTANCE.updateFromDto(aircraftCategoryDto, aircraftCategory);
        aircraftCategory = aircraftCategoryRepository.save(aircraftCategory);

        return AircraftCategoryMapper.INSTANCE.toDtoWithSubcategories(aircraftCategory);
    }

    @Override
    public void deleteAircraftCategory(Long id) {
        if (!aircraftCategoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aircraft Category not found with id: " + id);
        }

        aircraftCategoryRepository.deleteById(id);
    }

    @Override
    public AircraftCategoryDto findById(Long id) {
        return AircraftCategoryMapper.INSTANCE.toDtoWithSubcategories(aircraftCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft Category not found with id: " + id)));
    }

    @Override
    public Page<AircraftCategoryDto> findAllPaginated(CoreCriteria criteria, Pageable pageable) {
        return aircraftCategoryRepository.findAircraftCategoriesByCriteria(criteria, pageable)
                .map(AircraftCategoryMapper.INSTANCE::toDtoWithSubcategories);
    }
}
