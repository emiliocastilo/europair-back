package com.europair.management.impl.service.airport;

import com.europair.management.api.dto.regions.RegionDTO;
import com.europair.management.impl.mappers.regions.IRegionMapper;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.airport.repository.AirportRepository;
import com.europair.management.rest.model.regions.entity.Region;
import com.europair.management.rest.model.regionsairports.entity.RegionAirport;
import com.europair.management.rest.model.regionsairports.entity.RegionAirportPK;
import com.europair.management.rest.model.regionsairports.repository.IRegionAirportRepository;
import com.europair.management.rest.model.regionscountries.repository.IRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class AirportRegionServiceImpl implements IAirportRegionService {

    @Autowired
    private IRegionAirportRepository regionsAirportsRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private IRegionRepository regionRepository;

    @Override
    public Page<RegionDTO> findAllPaginatedByFilter(final Long airportId, Pageable pageable) {
        checkIfAirportExists(airportId);

        return regionsAirportsRepository.findByAirportId(airportId, pageable).map(regionAirport ->
                IRegionMapper.INSTANCE.toSimpleDto(regionAirport.getRegion()));
    }

    @Override
    public RegionDTO findById(final Long airportId, Long id) {
        checkIfAirportExists(airportId);
        RegionAirportPK regionAirportId = new RegionAirportPK();
        regionAirportId.setAirportId(airportId);
        regionAirportId.setRegionId(id);
        RegionAirport regionAirport = regionsAirportsRepository.findById(regionAirportId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Region-Airport not found with id: " + id));

        return IRegionMapper.INSTANCE.toSimpleDto(regionAirport.getRegion());
    }

    @Override
    public RegionDTO saveRegionAirport(final Long airportId, RegionDTO regionDto) {
        Airport airport = airportRepository.findById(airportId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Airport not found with id: " + airportId));
        Region region = regionRepository.findById(regionDto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Region not found with id: " + regionDto.getId()));

        RegionAirport regionAirport = new RegionAirport();
        regionAirport.setAirport(airport);
        regionAirport.setRegion(region);

        RegionAirportPK regionAirportPK = new RegionAirportPK();
        regionAirportPK.setAirportId(airportId);
        regionAirportPK.setRegionId(regionDto.getId());
        regionAirport.setId(regionAirportPK);

        regionAirport = regionsAirportsRepository.save(regionAirport);

        return IRegionMapper.INSTANCE.toSimpleDto(regionAirport.getRegion());
    }

    @Override
    public void deleteRegionAirport(final Long airportId, Long id) {
        checkIfAirportExists(airportId);
        RegionAirportPK regionAirportId = new RegionAirportPK();
        regionAirportId.setAirportId(airportId);
        regionAirportId.setRegionId(id);
        if (!regionsAirportsRepository.existsById(regionAirportId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Region-Airport not found with id: " + id);
        }
        regionsAirportsRepository.deleteById(regionAirportId);
    }

    private void checkIfAirportExists(final Long airportId) {
        if (!airportRepository.existsById(airportId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Airport not found with id: " + airportId);
        }
    }

}
