package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftBaseDto;
import com.europair.management.impl.common.exception.InvalidArgumentException;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.fleet.IAircraftBaseMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.airport.repository.AirportRepository;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.fleet.entity.Aircraft;
import com.europair.management.rest.model.fleet.entity.AircraftBase;
import com.europair.management.rest.model.fleet.repository.AircraftBaseRepository;
import com.europair.management.rest.model.fleet.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AircraftBaseServiceImpl implements IAircraftBaseService {

    private final String AIRCRAFT_ID_FILTER = "aircraft.id";

    @Autowired
    private AircraftBaseRepository aircraftBaseRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Override
    public Page<AircraftBaseDto> findAllPaginatedByFilter(final Long aircraftId, Pageable pageable, CoreCriteria criteria) {
        checkIfAircraftExists(aircraftId);
        Utils.addCriteriaIfNotExists(criteria, AIRCRAFT_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(aircraftId));

        return aircraftBaseRepository.findAircraftBaseByCriteria(criteria, pageable)
                .map(IAircraftBaseMapper.INSTANCE::toDto);
    }

    @Override
    public AircraftBaseDto findById(final Long aircraftId, Long id) {
        checkIfAircraftExists(aircraftId);
        return IAircraftBaseMapper.INSTANCE.toDto(aircraftBaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Base not found with id: " + id)));
    }

    @Override
    public AircraftBaseDto saveAircraftBase(final Long aircraftId, AircraftBaseDto aircraftBaseDto) {
        checkIfAircraftExists(aircraftId);
        checkIfAirportExists(aircraftBaseDto.getAirport().getId());
        if (aircraftBaseDto.getId() != null) {
            throw new InvalidArgumentException(String.format("New Base expected. Identifier %s got", aircraftBaseDto.getId()));
        }

        AircraftBase aircraftBase = IAircraftBaseMapper.INSTANCE.toEntity(aircraftBaseDto);

        // Set relationships
        Aircraft aircraft = new Aircraft();
        aircraft.setId(aircraftId);
        aircraftBase.setAircraft(aircraft);
        Airport airport = new Airport();
        airport.setId(aircraftBaseDto.getAirport().getId());
        aircraftBase.setAirport(airport);

        aircraftBase = aircraftBaseRepository.save(aircraftBase);

        return IAircraftBaseMapper.INSTANCE.toSimpleDto(aircraftBase);
    }

    @Override
    public AircraftBaseDto updateAircraftBase(final Long aircraftId, Long id, AircraftBaseDto aircraftBaseDto) {
        checkIfAircraftExists(aircraftId);
        checkIfAirportExists(aircraftBaseDto.getAirport().getId());
        AircraftBase aircraftBase = aircraftBaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Base not found with id: " + id));
        IAircraftBaseMapper.INSTANCE.updateFromDto(aircraftBaseDto, aircraftBase);

        // Change relationships
        if (!aircraftBase.getAirport().getId().equals(aircraftBaseDto.getAirport().getId())) {
            Airport airport = new Airport();
            airport.setId(aircraftBaseDto.getAirport().getId());
            aircraftBase.setAirport(airport);
        }

        aircraftBase = aircraftBaseRepository.save(aircraftBase);

        return IAircraftBaseMapper.INSTANCE.toSimpleDto(aircraftBase);
    }

    @Override
    public void deleteAircraftBase(final Long aircraftId, Long id) {
        checkIfAircraftExists(aircraftId);
        if (!aircraftBaseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Base not found with id: " + id);
        }
        aircraftBaseRepository.deleteById(id);
    }

    private void checkIfAirportExists(final Long airportId) {
        if (!airportRepository.existsById(airportId)) {
            throw new ResourceNotFoundException("Airport not found with id: " + airportId);
        }
    }

    private void checkIfAircraftExists(final Long aircraftId) {
        if (!airportRepository.existsById(aircraftId)) {
            throw new ResourceNotFoundException("Aircraft not found with id: " + aircraftId);
        }
    }

}
