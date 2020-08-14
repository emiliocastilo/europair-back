package com.europair.management.impl.service.fleet;


import com.europair.management.api.dto.fleet.AircraftDto;
import com.europair.management.impl.common.exception.InvalidArgumentException;
import com.europair.management.impl.common.exception.ResourceNotFoundException;

import com.europair.management.impl.mappers.fleet.AircraftMapper;
import com.europair.management.rest.model.common.CoreCriteria;

import com.europair.management.rest.model.fleet.entity.Aircraft;
import com.europair.management.rest.model.fleet.entity.AircraftBase;

import com.europair.management.rest.model.fleet.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional
public class AircraftServiceImpl implements IAircraftService {

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

    @Override
    public AircraftDto saveAircraft(final AircraftDto aircraftDto) {

        if (aircraftDto.getId() != null) {
            throw new InvalidArgumentException(String.format("New aircraft expected. Identifier %s got", aircraftDto.getId()));
        }
        Aircraft aircraft = AircraftMapper.INSTANCE.toEntity(aircraftDto);
        if (!CollectionUtils.isEmpty(aircraft.getBases())) {
            for (AircraftBase base : aircraft.getBases()) {
                base.setAircraft(aircraft);
            }
        }

        aircraft = aircraftRepository.save(aircraft);
        return AircraftMapper.INSTANCE.toDto(aircraft);
    }

    @Override
    public AircraftDto updateAircraft(Long id, AircraftDto aircraftDto) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found with id: " + id));

        AircraftMapper.INSTANCE.updateFromDto(aircraftDto, aircraft);
        if (!CollectionUtils.isEmpty(aircraft.getBases())) {
            for (AircraftBase base : aircraft.getBases()) {
                base.setAircraft(aircraft);
            }
        }

        aircraft = aircraftRepository.save(aircraft);


        return AircraftMapper.INSTANCE.toDto(aircraft);
    }

    @Override
    public void deleteAircraft(Long id) {
        if (!aircraftRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aircraft not found with id: " + id);
        }

        aircraftRepository.deleteById(id);
    }
}