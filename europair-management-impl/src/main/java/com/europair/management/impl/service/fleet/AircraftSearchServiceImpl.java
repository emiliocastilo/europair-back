package com.europair.management.impl.service.fleet;


import com.europair.management.api.dto.fleet.AircraftDto;
import com.europair.management.api.dto.fleet.AircraftFilterDto;
import com.europair.management.impl.common.exception.InvalidArgumentException;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.fleet.IAircraftMapper;
import com.europair.management.rest.model.audit.entity.SoftRemovableBaseEntity;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.common.Restriction;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import com.europair.management.rest.model.fleet.entity.AircraftCategory;
import com.europair.management.rest.model.fleet.entity.AircraftType;
import com.europair.management.rest.model.fleet.repository.AircraftCategoryRepository;
import com.europair.management.rest.model.fleet.repository.AircraftRepository;
import com.europair.management.rest.model.operators.entity.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class AircraftSearchServiceImpl implements IAircraftSearchService {

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AircraftCategoryRepository aircraftCategoryRepository;


    @Override
    public List<AircraftDto> searchAircraft(final AircraftFilterDto filterDto) {
        CoreCriteria criteria = new CoreCriteria();
        criteria.setRestrictions(new ArrayList<>());

        // Active filter
        criteria.getRestrictions().add(Restriction.builder()
                .column(SoftRemovableBaseEntity.REMOVED_AT)
                .operator(OperatorEnum.IS_NULL)
                .build());

        // Airport filter
        criteria.getRestrictions().add(Restriction.builder()
                .column("bases.airport.id")
                .operator(OperatorEnum.EQUALS)
                .value(String.valueOf(filterDto.getBaseIds()))
                .build());

        // Insurance date filter
        if (filterDto.getEndDate() != null) {
            criteria.getRestrictions().add(Restriction.builder()
                    .column("insuranceEndDate")
                    .operator(OperatorEnum.GREATER_OR_EQ)
                    .value(String.valueOf(filterDto.getEndDate()))
                    .build());
        }

        // Ambulance filter
        if (Boolean.TRUE.equals(filterDto.getAmbulance())) {
            criteria.getRestrictions().add(Restriction.builder()
                    .column("ambulance")
                    .operator(OperatorEnum.EQUALS)
                    .value(String.valueOf(filterDto.getAmbulance()))
                    .build());
        }

        List<Aircraft> aircraftFiltered = new ArrayList<>();
//                aircraftRepository.searchAircraft(criteria);

        // Post Criteria filters
        Stream<Aircraft> aircraftStream = aircraftFiltered.stream();

        // Daytime config filter
        if (filterDto.getSeats() != null) {
            aircraftStream = aircraftStream.filter(a -> filterDto.getSeats() <=
                    ((a.getSeatingF() == null ? 0 : a.getSeatingF())
                            + (a.getSeatingC() == null ? 0 : a.getSeatingC())
                            + (a.getSeatingY() == null ? 0 : a.getSeatingY())));
        }

        // Nighttime config filter
        if (filterDto.getBeds() != null) {
            aircraftStream = aircraftStream.filter(a -> filterDto.getBeds() <=
                    (a.getNighttimeConfiguration() != null ? a.getNighttimeConfiguration() :
                            ((a.getSeatingF() == null ? 0 : a.getSeatingF())
                                    + (a.getSeatingC() == null ? 0 : a.getSeatingC())
                                    + (a.getSeatingY() == null ? 0 : a.getSeatingY()))));
        }

        // Category filter
        if (filterDto.getSubcategoryId() != null) {
            AircraftCategory filteredSubcategory = aircraftCategoryRepository.findById(filterDto.getSubcategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + filterDto.getSubcategoryId()));

            aircraftStream = aircraftStream.filter(a ->
                    filterDto.getSubcategoryId().equals(a.getAircraftType().getSubcategory().getId()) ||
                            (a.getAircraftType().getCategory().getId().equals(filteredSubcategory.getParentCategory().getId()) &&
                                    a.getAircraftType().getSubcategory().getOrder() >= filteredSubcategory.getOrder()));
        }


        // ToDo filtro por fechas requiere el repositorio de expedientes al menos.

        List<AircraftDto> result = aircraftStream.map(IAircraftMapper.INSTANCE::toDto).collect(Collectors.toList());

        return result;
    }
}
