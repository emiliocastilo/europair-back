package com.europair.management.impl.service.fleet;


import com.europair.management.api.dto.fleet.AircraftFilterDto;
import com.europair.management.api.dto.fleet.AircraftSearchResultDataDto;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.fleet.IAircraftSearchMapper;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import com.europair.management.rest.model.fleet.entity.AircraftCategory;
import com.europair.management.rest.model.fleet.repository.AircraftCategoryRepository;
import com.europair.management.rest.model.fleet.repository.AircraftRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AircraftSearchServiceImpl implements IAircraftSearchService {

    @Value("${europair.aircraft.search.default.category.operation.acmi}")
    private String DEFAULT_TYPE_FOR_OPERATION_ACMI;

    @Value("${europair.aircraft.search.default.category.operation.commercial}")
    private String DEFAULT_TYPE_FOR_OPERATION_COMMERCIAL;

    @Value("${europair.aircraft.search.default.category.operation.executive}")
    private String DEFAULT_TYPE_FOR_OPERATION_EXECUTIVE;

    @Value("${europair.aircraft.search.default.category.operation.charge}")
    private String DEFAULT_TYPE_FOR_OPERATION_CHARGE;

    @Value("${europair.aircraft.search.default.category.operation.group}")
    private String DEFAULT_TYPE_FOR_OPERATION_GROUP;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AircraftCategoryRepository aircraftCategoryRepository;


    @Override
    public List<AircraftSearchResultDataDto> searchAircraft(final AircraftFilterDto filterDto) {


        // ToDo checek near airports and add ids to filter


        if (filterDto.getOperationType() != null && filterDto.getCategoryId() == null) {
            String defaultTypeCode = switch (filterDto.getOperationType()) {
                case ACMI -> DEFAULT_TYPE_FOR_OPERATION_ACMI;
                case COMMERCIAL -> DEFAULT_TYPE_FOR_OPERATION_COMMERCIAL;
                case EXECUTIVE -> DEFAULT_TYPE_FOR_OPERATION_EXECUTIVE;
                case CHARGE -> DEFAULT_TYPE_FOR_OPERATION_CHARGE;
                case GROUP -> DEFAULT_TYPE_FOR_OPERATION_GROUP;
            };

            if (!Strings.isEmpty(defaultTypeCode)) {
                aircraftCategoryRepository.findByCode(defaultTypeCode)
                        .ifPresent(aircraftCategory -> filterDto.setCategoryId(aircraftCategory.getId()));
            }
        }

        Integer minSubcategory = null;
        if (filterDto.getSubcategoryId() != null) {
            AircraftCategory subCategory = aircraftCategoryRepository.findById(filterDto.getSubcategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with id; " + filterDto.getSubcategoryId()));
            if (!Boolean.TRUE.equals(filterDto.getExactSubcategory())) {
                minSubcategory = subCategory.getOrder();
                filterDto.setCategoryId(subCategory.getParentCategory().getId());
            }
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

        List<AircraftSearchResultDataDto> result = aircraftFiltered.stream()
                .map(IAircraftSearchMapper.INSTANCE::toDto)
                .collect(Collectors.toList());

        return result;
    }
}
