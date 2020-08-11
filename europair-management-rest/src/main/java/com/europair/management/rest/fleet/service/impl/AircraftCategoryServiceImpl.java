package com.europair.management.rest.fleet.service.impl;

import com.europair.management.rest.common.exception.InvalidArgumentException;
import com.europair.management.rest.common.exception.ResourceNotFoundException;
import com.europair.management.rest.fleet.repository.AircraftCategoryRepository;
import com.europair.management.rest.fleet.service.AircraftCategoryService;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.common.Restriction;
import com.europair.management.rest.model.fleet.dto.AircraftCategoryDto;
import com.europair.management.rest.model.fleet.entity.AircraftCategory;
import com.europair.management.rest.model.fleet.mapper.AircraftCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;

@Service
@Transactional
public class AircraftCategoryServiceImpl implements AircraftCategoryService {

    private final String PARENT_CATEGORY_FILTER = "parentCategory.id";

    @Autowired
    private AircraftCategoryRepository aircraftCategoryRepository;

    @Override
    public AircraftCategoryDto saveAircraftCategory(AircraftCategoryDto aircraftCategoryDto) {
        if (aircraftCategoryDto.getId() != null) {
            throw new InvalidArgumentException(String.format("New aircraft category expected. Identifier %s got",
                    aircraftCategoryDto.getId()));
        }

        // Parent category default values
        aircraftCategoryDto.setParentCategory(null);
        aircraftCategoryDto.setOrder(null);

        AircraftCategory aircraftCategory = AircraftCategoryMapper.INSTANCE.toEntity(aircraftCategoryDto);

        aircraftCategory = aircraftCategoryRepository.save(aircraftCategory);
        return AircraftCategoryMapper.INSTANCE.toDtoWithSubcategories(aircraftCategory);
    }

    @Override
    public AircraftCategoryDto updateAircraftCategory(Long id, AircraftCategoryDto aircraftCategoryDto) {
        AircraftCategory aircraftCategory = aircraftCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft Category not found with id: " + id));

        // Parent category default values
        aircraftCategoryDto.setParentCategory(null);
        aircraftCategoryDto.setOrder(null);

        AircraftCategoryMapper.INSTANCE.updateFromDto(aircraftCategoryDto, aircraftCategory);
        aircraftCategory = aircraftCategoryRepository.save(aircraftCategory);

        return AircraftCategoryMapper.INSTANCE.toDtoWithSubcategories(aircraftCategory);
    }

    @Override
    public void deleteAircraftCategory(Long id) {
        checkIfCategoryExists(id);
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

    /*
        SUBCATEGORIES
     */

    @Override
    public AircraftCategoryDto saveAircraftSubcategory(Long parentCategoryId, AircraftCategoryDto aircraftCategoryDto) {
        checkIfCategoryExists(parentCategoryId);
        if (aircraftCategoryDto.getId() != null) {
            throw new InvalidArgumentException(String.format("New subcategory expected. Identifier %s got", aircraftCategoryDto.getId()));
        }

        AircraftCategoryDto parentCategory = new AircraftCategoryDto();
        parentCategory.setId(parentCategoryId);
        aircraftCategoryDto.setParentCategory(parentCategory);

        AircraftCategory aircraftCategory = AircraftCategoryMapper.INSTANCE.toEntity(aircraftCategoryDto);
        aircraftCategory = aircraftCategoryRepository.save(aircraftCategory);

        return AircraftCategoryMapper.INSTANCE.toDtoNoParent(aircraftCategory);
    }

    @Override
    public AircraftCategoryDto updateAircraftSubcategory(Long parentCategoryId, Long id, AircraftCategoryDto aircraftCategoryDto) {
        checkIfCategoryExists(parentCategoryId);
        AircraftCategory aircraftCategory = aircraftCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with id: " + id));

        AircraftCategoryMapper.INSTANCE.updateFromDto(aircraftCategoryDto, aircraftCategory);
        aircraftCategory = aircraftCategoryRepository.save(aircraftCategory);

        return AircraftCategoryMapper.INSTANCE.toDtoNoParent(aircraftCategory);
    }

    @Override
    public void deleteAircraftSubcategory(Long parentCategoryId, Long id) {
        checkIfCategoryExists(parentCategoryId);
        checkIfCategoryExists(id);
        aircraftCategoryRepository.deleteById(id);
    }

    @Override
    public AircraftCategoryDto findSubcategoryById(Long parentCategoryId, Long id) {
        checkIfCategoryExists(parentCategoryId);
        return AircraftCategoryMapper.INSTANCE.toDtoNoParent(aircraftCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft Subcategory not found with id: " + id)));
    }

    @Override
    public Page<AircraftCategoryDto> findAllSubcategoriesPaginated(Long parentCategoryId, CoreCriteria criteria, Pageable pageable) {
        checkIfCategoryExists(parentCategoryId);

        if (criteria == null || CollectionUtils.isEmpty(criteria.getRestrictions())) {
            criteria = new CoreCriteria();
            criteria.setRestrictions(new ArrayList<>());
        }

        // Add parent category filter if not present
        if (criteria.getRestrictions().stream()
                .noneMatch(restriction -> restriction.getColumn().equals(PARENT_CATEGORY_FILTER))) {
            criteria.getRestrictions().add(Restriction.builder()
                    .column(PARENT_CATEGORY_FILTER)
                    .value(String.valueOf(parentCategoryId))
                    .operator(OperatorEnum.EQUALS)
                    .build()
            );
        }

        return aircraftCategoryRepository.findAircraftCategoriesByCriteria(criteria, pageable)
                .map(AircraftCategoryMapper.INSTANCE::toDtoNoParent);
    }

    private void checkIfCategoryExists(final Long categoryId) {
        if (!aircraftCategoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Aircraft Category not found with id: " + categoryId);
        }
    }
}
