package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftTypeObservationDto;
import com.europair.management.impl.mappers.fleet.IAircraftTypeObservationMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.fleet.entity.AircraftType;
import com.europair.management.rest.model.fleet.entity.AircraftTypeObservation;
import com.europair.management.rest.model.fleet.repository.AircraftTypeObservationRepository;
import com.europair.management.rest.model.fleet.repository.AircraftTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class AircraftTypeObservationServiceImpl implements IAircraftTypeObservationService {

    private final String AIRCRAFT_TYPE_ID_FILTER = "aircraftType.id";

    @Autowired
    private AircraftTypeObservationRepository aircraftTypeObservationRepository;

    @Autowired
    private AircraftTypeRepository aircraftTypeRepository;

    @Override
    public Page<AircraftTypeObservationDto> findAllPaginatedByFilter(final Long aircraftTypeId, Pageable pageable, CoreCriteria criteria) {
        checkIfAircraftTypeExists(aircraftTypeId);
        Utils.addCriteriaIfNotExists(criteria, AIRCRAFT_TYPE_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(aircraftTypeId));

        return aircraftTypeObservationRepository.findAircraftTypeObservationByCriteria(criteria, pageable)
                .map(IAircraftTypeObservationMapper.INSTANCE::toDto);
    }

    @Override
    public AircraftTypeObservationDto findById(final Long aircraftTypeId, Long id) {
        checkIfAircraftTypeExists(aircraftTypeId);
        return IAircraftTypeObservationMapper.INSTANCE.toDto(aircraftTypeObservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Observation not found with id: " + id)));
    }

    @Override
    public AircraftTypeObservationDto saveAircraftTypeObservation(final Long aircraftTypeId, AircraftTypeObservationDto aircraftTypeObservationDto) {
        checkIfAircraftTypeExists(aircraftTypeId);
        if (aircraftTypeObservationDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New Observation expected. Identifier %s got", aircraftTypeObservationDto.getId()));
        }

        AircraftTypeObservation aircraftTypeObservation = IAircraftTypeObservationMapper.INSTANCE.toEntity(aircraftTypeObservationDto);

        // Set relationships
        AircraftType type = new AircraftType();
        type.setId(aircraftTypeId);
        aircraftTypeObservation.setAircraftType(type);

        aircraftTypeObservation = aircraftTypeObservationRepository.save(aircraftTypeObservation);

        return IAircraftTypeObservationMapper.INSTANCE.toDto(aircraftTypeObservation);
    }

    @Override
    public AircraftTypeObservationDto updateAircraftTypeObservation(final Long aircraftTypeId, Long id, AircraftTypeObservationDto aircraftTypeObservationDto) {
        checkIfAircraftTypeExists(aircraftTypeId);
        AircraftTypeObservation aircraftTypeObservation = aircraftTypeObservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Observation not found with id: " + id));
        IAircraftTypeObservationMapper.INSTANCE.updateFromDto(aircraftTypeObservationDto, aircraftTypeObservation);
        aircraftTypeObservation = aircraftTypeObservationRepository.save(aircraftTypeObservation);

        return IAircraftTypeObservationMapper.INSTANCE.toDto(aircraftTypeObservation);
    }

    @Override
    public void deleteAircraftTypeObservation(final Long aircraftTypeId, Long id) {
        checkIfAircraftTypeExists(aircraftTypeId);
        if (!aircraftTypeObservationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Observation not found with id: " + id);
        }
        aircraftTypeObservationRepository.deleteById(id);
    }

    private void checkIfAircraftTypeExists(final Long aircraftTypeId) {
        if (!aircraftTypeRepository.existsById(aircraftTypeId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aircraft Type not found with id: " + aircraftTypeId);
        }
    }

}
