package com.europair.management.impl.service.airport;

import com.europair.management.api.dto.airport.RunwayDto;
import com.europair.management.api.dto.conversions.common.Unit;
import com.europair.management.impl.common.exception.InvalidArgumentException;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.airport.IRunwayMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.airport.entity.Runway;
import com.europair.management.rest.model.airport.repository.AirportRepository;
import com.europair.management.rest.model.airport.repository.RunwayRepository;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class RunwayServiceImpl implements IRunwayService {

    private final String AIRPORT_ID_FILTER = "airport.id";
    private final Unit DEFAULT_UNIT = Unit.METER;

    @Autowired
    private RunwayRepository runwayRepository;

    @Autowired
    private AirportRepository airportRepository;

//    @Autowired
//    private ConversionService conversionService;

    @Override
    public Page<RunwayDto> findAllPaginatedByFilter(final Long airportId, Pageable pageable, CoreCriteria criteria) {
        checkIfAirportExists(airportId);
        Utils.addCriteriaIfNotExists(criteria, AIRPORT_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(airportId));

        return runwayRepository.findRunwayByCriteria(criteria, pageable)
                .map(IRunwayMapper.INSTANCE::toDto);
    }

    @Override
    public RunwayDto findById(final Long airportId, Long id) {
        checkIfAirportExists(airportId);
        return IRunwayMapper.INSTANCE.toDto(runwayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Runway not found with id: " + id)));
    }

    @Override
    public RunwayDto saveRunway(final Long airportId, RunwayDto runwayDto) {
        checkIfAirportExists(airportId);
        if (runwayDto.getId() != null) {
            throw new InvalidArgumentException(String.format("New Runway expected. Identifier %s got", runwayDto.getId()));
        }

        Runway runway = IRunwayMapper.INSTANCE.toEntity(runwayDto);

        // Set relationships
        Airport airport = new Airport();
        airport.setId(airportId);
        runway.setAirport(airport);

        runway = runwayRepository.save(runway);
        updateMainRunway(airportId);

        Long runwayId = runway.getId();
        return IRunwayMapper.INSTANCE.toDto(runwayRepository.findById(runway.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Runway not found with id: " + runwayId)));
    }

    @Override
    public RunwayDto updateRunway(final Long airportId, Long id, RunwayDto runwayDto) {
        checkIfAirportExists(airportId);
        Runway runway = runwayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Runway not found with id: " + id));
        IRunwayMapper.INSTANCE.updateFromDto(runwayDto, runway);
        runway = runwayRepository.save(runway);
        updateMainRunway(airportId);

        return IRunwayMapper.INSTANCE.toDto(runwayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Runway not found with id: " + id)));
    }

    @Override
    public void deleteRunway(final Long airportId, Long id) {
        checkIfAirportExists(airportId);
        if (!runwayRepository.existsById(id)) {
            throw new ResourceNotFoundException("Runway not found with id: " + id);
        }
        runwayRepository.deleteById(id);
        updateMainRunway(airportId);
    }

    private void checkIfAirportExists(final Long airportId) {
        if (!airportRepository.existsById(airportId)) {
            throw new ResourceNotFoundException("Airport not found with id: " + airportId);
        }
    }

    private void updateMainRunway(final Long airportId) {
        List<Runway> runwayList = runwayRepository.findAllByAirportId(airportId);

        Runway main = runwayList.stream().filter(r -> Boolean.TRUE.equals(r.getMainRunway())).findAny().orElse(null);
        Runway max = runwayList.stream().max(Comparator.comparing(runway -> {
            /* ToDo: usar servicio de conversion, ahora está en otro paquete hará falta un refactor o import
            ConversionDataDTO.ConversionTuple ct = new ConversionDataDTO.ConversionTuple();
            ct.setDstUnit(DEFAULT_UNIT);
            ct.setSrcUnit(Unit.valueOf(runway.getLengthUnit().toString()));
            ct.setValue(runway.getLength());
            ConversionDataDTO conversionData = new ConversionDataDTO();
            conversionData.setDataToConvert(Collections.singletonList(ct));

            return conversionService.convertData(conversionData).get(0);
             */
            return runway.getLength();
        })).orElse(null);

        if (max != null && main != null && !max.getId().equals(main.getId())) {
            List<Runway> toUpdate = new ArrayList<>();

            main.setMainRunway(false);
            main.setName("");
            toUpdate.add(main);

            max.setMainRunway(true);
            max.setName("Pista más larga");
            toUpdate.add(max);

            runwayRepository.saveAll(toUpdate);

        } else if (max != null && main == null) {
            max.setMainRunway(true);
            max.setName("Pista más larga");
            runwayRepository.save(max);
        }

    }
}
