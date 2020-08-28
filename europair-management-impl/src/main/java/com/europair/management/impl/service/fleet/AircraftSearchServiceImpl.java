package com.europair.management.impl.service.fleet;


import com.europair.management.api.dto.fleet.AircraftDto;
import com.europair.management.api.dto.fleet.AircraftFilterDto;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.fleet.IAircraftMapper;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import com.europair.management.rest.model.fleet.entity.AircraftCategory;
import com.europair.management.rest.model.fleet.repository.AircraftCategoryRepository;
import com.europair.management.rest.model.fleet.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AircraftSearchServiceImpl implements IAircraftSearchService {

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AircraftCategoryRepository aircraftCategoryRepository;


    @Override
    public List<AircraftDto> searchAircraft(final AircraftFilterDto filterDto) {


        // ToDo checek near airports and add ids to filter

        Integer minSubcategory = null;
        if (filterDto.getSubcategoryId() != null && !Boolean.TRUE.equals(filterDto.getExactSubcategory())) {
            AircraftCategory subCategory = aircraftCategoryRepository.findById(filterDto.getSubcategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with id; " + filterDto.getSubcategoryId()));
            minSubcategory = subCategory.getOrder();
        }

        List<Aircraft> aircraftFiltered = aircraftRepository.searchAircraft(
                filterDto.getBaseIds(),
                filterDto.getCountryIds(),
                filterDto.getSeats(),
                filterDto.getBeds(),
                filterDto.getCategoryId(),
                filterDto.getSubcategoryId(),
                filterDto.getExactSubcategory(),
                minSubcategory,
                filterDto.getAmbulance(),
                filterDto.getAircraftTypes(),
                filterDto.getOperators()
        );


        // ToDo filtro por fechas requiere el repositorio de expedientes al menos.

        // ToDo: mapear a Dto de respuesta (pendiente de crear)
        List<AircraftDto> result = aircraftFiltered.stream()
                .map(IAircraftMapper.INSTANCE::toDto)
                .collect(Collectors.toList());

        return result;
    }
}
