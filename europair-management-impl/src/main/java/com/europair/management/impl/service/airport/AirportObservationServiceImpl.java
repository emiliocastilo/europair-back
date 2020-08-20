package com.europair.management.impl.service.airport;

import com.europair.management.api.dto.airport.AirportObservationDto;
import com.europair.management.impl.common.exception.InvalidArgumentException;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.airport.IAirportObservationMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.airport.entity.AirportObservation;
import com.europair.management.rest.model.airport.repository.AirportObservationRepository;
import com.europair.management.rest.model.airport.repository.AirportRepository;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AirportObservationServiceImpl implements IAirportObservationService {

    private final String AIRPORT_ID_FILTER = "airport.id";

    @Autowired
    private AirportObservationRepository airportObservationRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Override
    public Page<AirportObservationDto> findAllPaginatedByFilter(final Long airportId, Pageable pageable, CoreCriteria criteria) {
        checkIfAirportExists(airportId);
        Utils.addCriteriaIfNotExists(criteria, AIRPORT_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(airportId));

        return airportObservationRepository.findAirportObservationByCriteria(criteria, pageable)
                .map(IAirportObservationMapper.INSTANCE::toDto);
    }

    @Override
    public AirportObservationDto findById(final Long airportId, Long id) {
        checkIfAirportExists(airportId);
        return IAirportObservationMapper.INSTANCE.toDto(airportObservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AirportObservation not found with id: " + id)));
    }

    @Override
    public AirportObservationDto saveAirportObservation(final Long airportId, AirportObservationDto airportObservationDto) {
        checkIfAirportExists(airportId);
        if (airportObservationDto.getId() != null) {
            throw new InvalidArgumentException(String.format("New AirportObservation expected. Identifier %s got", airportObservationDto.getId()));
        }

        AirportObservation airportObservation = IAirportObservationMapper.INSTANCE.toEntity(airportObservationDto);

        // Set relationships
        Airport airport = new Airport();
        airport.setId(airportId);
        airportObservation.setAirport(airport);

        airportObservation = airportObservationRepository.save(airportObservation);

        return IAirportObservationMapper.INSTANCE.toDto(airportObservation);
    }

    @Override
    public AirportObservationDto updateAirportObservation(final Long airportId, Long id, AirportObservationDto airportObservationDto) {
        checkIfAirportExists(airportId);
        AirportObservation airportObservation = airportObservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AirportObservation not found with id: " + id));
        IAirportObservationMapper.INSTANCE.updateFromDto(airportObservationDto, airportObservation);
        airportObservation = airportObservationRepository.save(airportObservation);

        return IAirportObservationMapper.INSTANCE.toDto(airportObservation);
    }

    @Override
    public void deleteAirportObservation(final Long airportId, Long id) {
        checkIfAirportExists(airportId);
        if (!airportObservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("AirportObservation not found with id: " + id);
        }
        airportObservationRepository.deleteById(id);
    }

    private void checkIfAirportExists(final Long airportId) {
        if (!airportRepository.existsById(airportId)) {
            throw new ResourceNotFoundException("Airport not found with id: " + airportId);
        }
    }

}
