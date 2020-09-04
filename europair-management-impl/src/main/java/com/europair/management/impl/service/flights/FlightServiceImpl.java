package com.europair.management.impl.service.flights;

import com.europair.management.api.dto.flights.FlightDTO;
import com.europair.management.impl.common.exception.InvalidArgumentException;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.flights.IFlightMapper;
import com.europair.management.rest.model.flights.entity.Flight;
import com.europair.management.rest.model.flights.repository.IFlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FlightServiceImpl implements IFlightService {

    @Autowired
    private IFlightRepository flightRepository;

    @Override
    public Page<FlightDTO> findAllPaginated(Pageable pageable) {
      return flightRepository.findAll(pageable).map(IFlightMapper.INSTANCE::toDto);
    }

    @Override
    public FlightDTO findById(Long id) {
      return IFlightMapper.INSTANCE.toDto(flightRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id)));
    }

    @Override
    @Transactional(readOnly = false)
    public FlightDTO saveFlight(final FlightDTO flightDTO) {

      if (flightDTO.getId() != null) {
        throw new InvalidArgumentException(String.format("New flight expected. Identifier %s got", flightDTO.getId()));
      }
      Flight flight = IFlightMapper.INSTANCE.toEntity(flightDTO);
      flight = flightRepository.save(flight);

      return IFlightMapper.INSTANCE.toDto(flight);
    }

    @Override
    @Transactional(readOnly = false)
    public FlightDTO updateFlight(Long id, FlightDTO flightDTO) {
      Flight flight = flightRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));

      IFlightMapper.INSTANCE.updateFromDto(flightDTO, flight);
      flight = flightRepository.save(flight);

      return IFlightMapper.INSTANCE.toDto(flight);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteFlight(Long id) {
      if (!flightRepository.existsById(id)) {
        throw new ResourceNotFoundException("Flight not found with id: " + id);
      }
      flightRepository.deleteById(id);
    }

}
