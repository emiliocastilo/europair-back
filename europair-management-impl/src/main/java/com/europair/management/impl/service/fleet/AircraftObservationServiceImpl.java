package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftObservationDto;
import com.europair.management.impl.mappers.fleet.IAircraftObservationMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import com.europair.management.rest.model.fleet.entity.AircraftObservation;
import com.europair.management.rest.model.fleet.repository.AircraftObservationRepository;
import com.europair.management.rest.model.fleet.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class AircraftObservationServiceImpl implements IAircraftObservationService {

    private final String AIRPORT_ID_FILTER = "aircraft.id";

    @Autowired
    private AircraftObservationRepository aircraftObservationRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Override
    public Page<AircraftObservationDto> findAllPaginatedByFilter(final Long aircraftId, Pageable pageable, CoreCriteria criteria) {
        checkIfAircraftExists(aircraftId);
        Utils.addCriteriaIfNotExists(criteria, AIRPORT_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(aircraftId));

        return aircraftObservationRepository.findAircraftObservationByCriteria(criteria, pageable)
                .map(IAircraftObservationMapper.INSTANCE::toDto);
    }

    @Override
    public AircraftObservationDto findById(final Long aircraftId, Long id) {
        checkIfAircraftExists(aircraftId);
        return IAircraftObservationMapper.INSTANCE.toDto(aircraftObservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AircraftObservation not found with id: " + id)));
    }

    @Override
    public AircraftObservationDto saveAircraftObservation(final Long aircraftId, AircraftObservationDto aircraftObservationDto) {
        checkIfAircraftExists(aircraftId);
        if (aircraftObservationDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New AircraftObservation expected. Identifier %s got", aircraftObservationDto.getId()));
        }

        AircraftObservation aircraftObservation = IAircraftObservationMapper.INSTANCE.toEntity(aircraftObservationDto);

        // Set relationships
        Aircraft aircraft = new Aircraft();
        aircraft.setId(aircraftId);
        aircraftObservation.setAircraft(aircraft);

        aircraftObservation = aircraftObservationRepository.save(aircraftObservation);

        return IAircraftObservationMapper.INSTANCE.toDto(aircraftObservation);
    }

    @Override
    public AircraftObservationDto updateAircraftObservation(final Long aircraftId, Long id, AircraftObservationDto aircraftObservationDto) {
        checkIfAircraftExists(aircraftId);
        AircraftObservation aircraftObservation = aircraftObservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AircraftObservation not found with id: " + id));
        IAircraftObservationMapper.INSTANCE.updateFromDto(aircraftObservationDto, aircraftObservation);
        aircraftObservation = aircraftObservationRepository.save(aircraftObservation);

        return IAircraftObservationMapper.INSTANCE.toDto(aircraftObservation);
    }

    @Override
    public void deleteAircraftObservation(final Long aircraftId, Long id) {
        checkIfAircraftExists(aircraftId);
        if (!aircraftObservationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "AircraftObservation not found with id: " + id);
        }
        aircraftObservationRepository.deleteById(id);
    }

    private void checkIfAircraftExists(final Long aircraftId) {
        if (!aircraftRepository.existsById(aircraftId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aircraft not found with id: " + aircraftId);
        }
    }

}
