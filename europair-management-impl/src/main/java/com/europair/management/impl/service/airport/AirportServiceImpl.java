package com.europair.management.impl.service.airport;

import com.europair.management.api.dto.airport.AirportDto;
import com.europair.management.impl.common.exception.InvalidArgumentException;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.airport.IAirportMapper;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.airport.repository.AirportRepository;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class AirportServiceImpl implements IAirportService {

    @Autowired
    private AirportRepository airportRepository;

    @Override
    public Page<AirportDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return airportRepository.findAirportByCriteria(criteria, pageable).map(IAirportMapper.INSTANCE::toDto);
    }

    @Override
    public AirportDto findById(Long id) {
        return IAirportMapper.INSTANCE.toDto(airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with id: " + id)));
    }

    @Override
    public AirportDto saveAirport(AirportDto airportDto) {
        if (airportDto.getId() != null) {
            throw new InvalidArgumentException(String.format("New Airport expected. Identifier %s got", airportDto.getId()));
        }

        Airport airport = IAirportMapper.INSTANCE.toEntity(airportDto);
        airport = airportRepository.save(airport);

        return IAirportMapper.INSTANCE.toDto(airport);
    }

    @Override
    public AirportDto updateAirport(Long id, AirportDto airportDto) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with id: " + id));
        IAirportMapper.INSTANCE.updateFromDto(airportDto, airport);
        airport = airportRepository.save(airport);

        return IAirportMapper.INSTANCE.toDto(airport);
    }

    @Override
    public void deleteAirport(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with id: " + id));

        airport.setRemovedAt(new Date());
        airportRepository.save(airport);
    }
}
