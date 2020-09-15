package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftTypeDto;
import com.europair.management.impl.mappers.fleet.IAircraftTypeMapper;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.exception.InvalidArgumentException;
import com.europair.management.rest.model.fleet.entity.AircraftCategory;
import com.europair.management.rest.model.fleet.entity.AircraftType;
import com.europair.management.rest.model.fleet.repository.AircraftTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
@Transactional
public class AircraftTypeServiceImpl implements IAircraftTypeService {

    @Autowired
    private AircraftTypeRepository aircraftTypeRepository;

    @Override
    public Page<AircraftTypeDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return aircraftTypeRepository.findAircraftTypeByCriteria(criteria, pageable).map(IAircraftTypeMapper.INSTANCE::toDto);
    }

    @Override
    public AircraftTypeDto findById(Long id) {
        return IAircraftTypeMapper.INSTANCE.toDto(aircraftTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AircraftType not found with id: " + id)));
    }

    @Override
    public AircraftTypeDto saveAircraftType(AircraftTypeDto aircraftTypeDto) {
        if (aircraftTypeDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New AircraftType expected. Identifier %s got", aircraftTypeDto.getId()));
        }

        AircraftType aircraftType = IAircraftTypeMapper.INSTANCE.toEntity(aircraftTypeDto);

        // Set relationships
        AircraftCategory category = new AircraftCategory();
        category.setId(aircraftTypeDto.getCategory().getId());
        aircraftType.setCategory(category);
        AircraftCategory subcategory = new AircraftCategory();
        subcategory.setId(aircraftTypeDto.getSubcategory().getId());
        aircraftType.setSubcategory(subcategory);

        aircraftType = aircraftTypeRepository.save(aircraftType);

        return IAircraftTypeMapper.INSTANCE.toDto(aircraftType);
    }

    @Override
    public AircraftTypeDto updateAircraftType(Long id, AircraftTypeDto aircraftTypeDto) {
        AircraftType aircraftType = aircraftTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AircraftType not found with id: " + id));
        IAircraftTypeMapper.INSTANCE.updateFromDto(aircraftTypeDto, aircraftType);

        // Update relationships
        if (!aircraftType.getCategory().getId().equals(aircraftTypeDto.getCategory().getId())) {
            AircraftCategory category = new AircraftCategory();
            category.setId(aircraftTypeDto.getCategory().getId());
            aircraftType.setCategory(category);
        }
        if (!aircraftType.getSubcategory().getId().equals(aircraftTypeDto.getSubcategory().getId())) {
            AircraftCategory subcategory = new AircraftCategory();
            subcategory.setId(aircraftTypeDto.getSubcategory().getId());
            aircraftType.setSubcategory(subcategory);
        }

        aircraftType = aircraftTypeRepository.save(aircraftType);

        return IAircraftTypeMapper.INSTANCE.toDto(aircraftType);
    }

    @Override
    public void deleteAircraftType(Long id) {
        AircraftType aircraftType = aircraftTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AircraftType not found with id: " + id));

        aircraftType.setRemovedAt(LocalDate.now());
        aircraftTypeRepository.save(aircraftType);
    }

}
