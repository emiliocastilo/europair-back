package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftDto;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.mappers.fleet.IAircraftMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import com.europair.management.rest.model.fleet.entity.AircraftType;
import com.europair.management.rest.model.fleet.repository.AircraftCategoryRepository;
import com.europair.management.rest.model.fleet.repository.AircraftRepository;
import com.europair.management.rest.model.operators.entity.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AircraftServiceImpl implements IAircraftService {

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AircraftCategoryRepository aircraftCategoryRepository;

    @Override
    public AircraftDto findById(Long id) {
        return IAircraftMapper.INSTANCE.toDto(aircraftRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRCRAFT_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    public Page<AircraftDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return aircraftRepository.findAircraftsByCriteria(criteria, pageable)
                .map(IAircraftMapper.INSTANCE::toDto);
    }

    @Override
    public AircraftDto saveAircraft(final AircraftDto aircraftDto) {

        if (aircraftDto.getId() != null) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRCRAFT_NEW_WITH_ID, String.valueOf(aircraftDto.getId()));
        }
        Aircraft aircraft = IAircraftMapper.INSTANCE.toEntity(aircraftDto);

        // Set relationships
        Operator operator = new Operator();
        operator.setId(aircraftDto.getOperator().getId());
        aircraft.setOperator(operator);
        AircraftType aircraftType = new AircraftType();
        aircraftType.setId(aircraftDto.getAircraftType().getId());
        aircraft.setAircraftType(aircraftType);

        aircraft = aircraftRepository.save(aircraft);

        return IAircraftMapper.INSTANCE.toDto(aircraft);
    }

    @Override
    public AircraftDto updateAircraft(Long id, AircraftDto aircraftDto) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRCRAFT_NOT_FOUND, String.valueOf(id)));

        IAircraftMapper.INSTANCE.updateFromDto(aircraftDto, aircraft);

        // Update relationships
        if (!aircraft.getOperator().getId().equals(aircraftDto.getOperator().getId())) {
            Operator operator = new Operator();
            operator.setId(aircraftDto.getOperator().getId());
            aircraft.setOperator(operator);
        }
        if (!aircraft.getAircraftType().getId().equals(aircraftDto.getAircraftType().getId())) {
            AircraftType aircraftType = new AircraftType();
            aircraftType.setId(aircraftDto.getAircraftType().getId());
            aircraft.setAircraftType(aircraftType);
        }

        aircraft = aircraftRepository.save(aircraft);

        return IAircraftMapper.INSTANCE.toDto(aircraft);
    }

    @Override
    public void deleteAircraft(Long id) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRCRAFT_NOT_FOUND, String.valueOf(id)));
        aircraft.setRemovedAt(LocalDateTime.now());
        aircraftRepository.save(aircraft);
    }

    @Override
    public void reactivateAircraft(@NotEmpty Set<Long> aircraftIds) {
        Set<Aircraft> aircraftSet = aircraftRepository.findByIdIn(aircraftIds).stream()
                .map(aircraft -> {
                    aircraft.setRemovedAt(null);
                    return aircraft;
                })
                .collect(Collectors.toSet());
        aircraftSet = new HashSet<>(aircraftRepository.saveAll(aircraftSet));
    }
}
