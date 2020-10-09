package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftDto;
import com.europair.management.impl.mappers.fleet.IAircraftMapper;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import com.europair.management.rest.model.fleet.entity.AircraftType;
import com.europair.management.rest.model.fleet.repository.AircraftCategoryRepository;
import com.europair.management.rest.model.fleet.repository.AircraftRepository;
import com.europair.management.rest.model.operators.entity.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aircraft not found with id: " + id)));
    }

    @Override
    public Page<AircraftDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return aircraftRepository.findAircraftsByCriteria(criteria, pageable)
                .map(IAircraftMapper.INSTANCE::toDto);
    }

    @Override
    public AircraftDto saveAircraft(final AircraftDto aircraftDto) {

        if (aircraftDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New aircraft expected. Identifier %s got", aircraftDto.getId()));
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aircraft not found with id: " + id));

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aircraft not found with id: " + id));
        aircraft.setRemovedAt(LocalDateTime.now());
        aircraftRepository.save(aircraft);
    }

}
