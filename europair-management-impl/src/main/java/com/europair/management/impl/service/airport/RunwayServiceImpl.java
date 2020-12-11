package com.europair.management.impl.service.airport;

import com.europair.management.api.dto.airport.RunwayDto;
import com.europair.management.api.dto.conversions.ConversionDataDTO;
import com.europair.management.api.dto.conversions.common.Unit;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.mappers.airport.IRunwayMapper;
import com.europair.management.impl.service.conversions.ConversionService;
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
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
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

    @Autowired
    private ConversionService conversionService;

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
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.RUNWAY_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    public RunwayDto saveRunway(final Long airportId, RunwayDto runwayDto) {
        checkIfAirportExists(airportId);
        if (runwayDto.getId() != null) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.RUNWAY_NEW_WITH_ID, String.valueOf(runwayDto.getId()));
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
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.RUNWAY_NOT_FOUND, String.valueOf(runwayId))));
    }

    @Override
    public RunwayDto updateRunway(final Long airportId, Long id, RunwayDto runwayDto) {
        checkIfAirportExists(airportId);
        Runway runway = runwayRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.RUNWAY_NOT_FOUND, String.valueOf(id)));
        IRunwayMapper.INSTANCE.updateFromDto(runwayDto, runway);
        runway = runwayRepository.save(runway);
        updateMainRunway(airportId);

        return IRunwayMapper.INSTANCE.toDto(runwayRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.RUNWAY_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    public void deleteRunway(final Long airportId, Long id) {
        checkIfAirportExists(airportId);
        if (!runwayRepository.existsById(id)) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.RUNWAY_NOT_FOUND, String.valueOf(id));
        }
        runwayRepository.deleteById(id);
        updateMainRunway(airportId);
    }

    private void checkIfAirportExists(final Long airportId) {
        if (!airportRepository.existsById(airportId)) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRPORT_NOT_FOUND, String.valueOf(airportId));
        }
    }

    private void updateMainRunway(final Long airportId) {
        List<Runway> runwayList = runwayRepository.findAllByAirportId(airportId);

        Runway main = runwayList.stream().filter(r -> Boolean.TRUE.equals(r.getMainRunway())).findAny().orElse(null);
        Runway max = runwayList.stream().max(Comparator.comparing(runway -> {
            ConversionDataDTO.ConversionTuple ct = new ConversionDataDTO.ConversionTuple();
            ct.setSrcUnit(runway.getLengthUnit());
            ct.setValue(runway.getLength());
            ConversionDataDTO conversionData = new ConversionDataDTO();
            conversionData.setDstUnit(DEFAULT_UNIT);
            conversionData.setDataToConvert(Collections.singletonList(ct));

            List<Double> result = conversionService.convertData(conversionData);
            return CollectionUtils.isEmpty(result) ? 0 : result.get(0);
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
