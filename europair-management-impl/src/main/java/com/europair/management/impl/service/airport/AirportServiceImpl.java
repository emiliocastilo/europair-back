package com.europair.management.impl.service.airport;

import com.europair.management.api.dto.airport.AirportDto;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.mappers.airport.IAirportMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.airport.repository.AirportRepository;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRPORT_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    public AirportDto saveAirport(AirportDto airportDto) {
        if (airportDto.getId() != null) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRPORT_NEW_WITH_ID, String.valueOf(airportDto.getId()));
        }

        Airport airport = IAirportMapper.INSTANCE.toEntity(airportDto);
        airport = airportRepository.save(airport);

        return IAirportMapper.INSTANCE.toDto(airport);
    }

    @Override
    public AirportDto updateAirport(Long id, AirportDto airportDto) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRPORT_NOT_FOUND, String.valueOf(id)));
        IAirportMapper.INSTANCE.updateFromDto(airportDto, airport);
        airport = airportRepository.save(airport);

        return IAirportMapper.INSTANCE.toDto(airport);
    }

    @Override
    public void deleteAirport(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRPORT_NOT_FOUND, String.valueOf(id)));

        airport.setRemovedAt(LocalDateTime.now());
        airportRepository.save(airport);
    }
}
