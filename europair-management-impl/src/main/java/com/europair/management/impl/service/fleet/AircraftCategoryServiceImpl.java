package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftCategoryDto;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.mappers.fleet.IAircraftCategoryMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.common.Restriction;
import com.europair.management.rest.model.fleet.entity.AircraftCategory;
import com.europair.management.rest.model.fleet.repository.AircraftCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
public class AircraftCategoryServiceImpl implements AircraftCategoryService {

    private final String PARENT_CATEGORY_ID_FILTER = "parentCategory.id";
    private final String PARENT_CATEGORY_FILTER = "parentCategory";

    @Autowired
    private AircraftCategoryRepository aircraftCategoryRepository;

    @Override
    public AircraftCategoryDto saveAircraftCategory(AircraftCategoryDto aircraftCategoryDto) {
        if (aircraftCategoryDto.getId() != null) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRCRAFT_CATEGORY_NEW_WITH_ID,
                    String.valueOf(aircraftCategoryDto.getId()));
        }

        // Parent category default values
        aircraftCategoryDto.setParentCategory(null);
        aircraftCategoryDto.setOrder(null);

        AircraftCategory aircraftCategory = IAircraftCategoryMapper.INSTANCE.toEntity(aircraftCategoryDto);

        aircraftCategory = aircraftCategoryRepository.save(aircraftCategory);
        return IAircraftCategoryMapper.INSTANCE.toDtoWithSubcategories(aircraftCategory);
    }

    @Override
    public AircraftCategoryDto updateAircraftCategory(Long id, AircraftCategoryDto aircraftCategoryDto) {
        AircraftCategory aircraftCategory = aircraftCategoryRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRCRAFT_CATEGORY_NOT_FOUND, String.valueOf(id)));

        // Parent category default values
        aircraftCategoryDto.setParentCategory(null);
        aircraftCategoryDto.setOrder(null);

        IAircraftCategoryMapper.INSTANCE.updateFromDto(aircraftCategoryDto, aircraftCategory);
        aircraftCategory = aircraftCategoryRepository.save(aircraftCategory);

        return IAircraftCategoryMapper.INSTANCE.toDtoWithSubcategories(aircraftCategory);
    }

    @Override
    public void deleteAircraftCategory(Long id) {
        checkIfCategoryExists(id);
        aircraftCategoryRepository.deleteById(id);
    }

    @Override
    public AircraftCategoryDto findById(Long id) {
        return IAircraftCategoryMapper.INSTANCE.toDtoWithSubcategories(aircraftCategoryRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRCRAFT_CATEGORY_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    public Page<AircraftCategoryDto> findAllPaginated(CoreCriteria criteria, Pageable pageable) {
        if (criteria == null || CollectionUtils.isEmpty(criteria.getRestrictions())) {
            criteria = new CoreCriteria();
            criteria.setRestrictions(new ArrayList<>());
        }

        // Add parent category filter if not present
        Optional<Restriction> parentCategoryRestriction = criteria.getRestrictions().stream()
                .filter(restriction -> restriction.getColumn().equals(PARENT_CATEGORY_FILTER))
                .findAny();
        if (parentCategoryRestriction.isPresent()) {
            parentCategoryRestriction.get().setOperator(OperatorEnum.IS_NULL);
            parentCategoryRestriction.get().setValue(null);

        } else {
            criteria.getRestrictions().add(Restriction.builder()
                    .column(PARENT_CATEGORY_FILTER)
                    .value(null)
                    .operator(OperatorEnum.IS_NULL)
                    .build()
            );
        }

        return aircraftCategoryRepository.findAircraftCategoriesByCriteria(criteria, pageable)
                .map(IAircraftCategoryMapper.INSTANCE::toDtoWithSubcategories);
    }

    /*
        SUBCATEGORIES
     */

    @Override
    public AircraftCategoryDto saveAircraftSubcategory(Long parentCategoryId, AircraftCategoryDto aircraftCategoryDto) {
        checkIfCategoryExists(parentCategoryId);
        if (aircraftCategoryDto.getId() != null) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRCRAFT_SUBCATEGORY_NEW_WITH_ID, String.valueOf(aircraftCategoryDto.getId()));
        }

        AircraftCategoryDto parentCategory = new AircraftCategoryDto();
        parentCategory.setId(parentCategoryId);
        aircraftCategoryDto.setParentCategory(parentCategory);

        AircraftCategory aircraftCategory = IAircraftCategoryMapper.INSTANCE.toEntity(aircraftCategoryDto);
        aircraftCategory = aircraftCategoryRepository.save(aircraftCategory);

        return IAircraftCategoryMapper.INSTANCE.toDtoNoParent(aircraftCategory);
    }

    @Override
    public AircraftCategoryDto updateAircraftSubcategory(Long parentCategoryId, Long id, AircraftCategoryDto aircraftCategoryDto) {
        checkIfCategoryExists(parentCategoryId);
        AircraftCategory aircraftCategory = aircraftCategoryRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRCRAFT_SUBCATEGORY_NOT_FOUND, String.valueOf(id)));

        IAircraftCategoryMapper.INSTANCE.updateFromDto(aircraftCategoryDto, aircraftCategory);
        aircraftCategory = aircraftCategoryRepository.save(aircraftCategory);

        return IAircraftCategoryMapper.INSTANCE.toDtoNoParent(aircraftCategory);
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
        return IAircraftCategoryMapper.INSTANCE.toDtoNoParent(aircraftCategoryRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRCRAFT_SUBCATEGORY_NOT_FOUND, String.valueOf(id))));
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
                .noneMatch(restriction -> restriction.getColumn().equals(PARENT_CATEGORY_ID_FILTER))) {
            criteria.getRestrictions().add(Restriction.builder()
                    .column(PARENT_CATEGORY_ID_FILTER)
                    .value(String.valueOf(parentCategoryId))
                    .operator(OperatorEnum.EQUALS)
                    .build()
            );
        }

        return aircraftCategoryRepository.findAircraftCategoriesByCriteria(criteria, pageable)
                .map(IAircraftCategoryMapper.INSTANCE::toDtoNoParent);
    }

    private void checkIfCategoryExists(final Long categoryId) {
        if (!aircraftCategoryRepository.existsById(categoryId)) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRCRAFT_CATEGORY_NOT_FOUND, String.valueOf(categoryId));
        }
    }
}
