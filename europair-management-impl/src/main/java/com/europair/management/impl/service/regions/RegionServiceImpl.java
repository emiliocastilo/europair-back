package com.europair.management.impl.service.regions;

import com.europair.management.api.dto.regions.RegionDTO;
import com.europair.management.impl.mappers.regions.IRegionMapper;
import com.europair.management.rest.model.airport.repository.AirportRepository;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.countries.repository.CountryRepository;
import com.europair.management.rest.model.regions.entity.Region;
import com.europair.management.rest.model.regionsairports.repository.IRegionAirportRepository;
import com.europair.management.rest.model.regionscountries.repository.IRegionCountryRepository;
import com.europair.management.rest.model.regions.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RegionServiceImpl implements IRegionService {

  private final RegionRepository regionRepository;
  private final CountryRepository countryRepository;
  private final IRegionCountryRepository regionCountryRepository;
  private final AirportRepository airportRepository;
  private final IRegionAirportRepository regionAirportRepository;

  @Override
  public Page<RegionDTO> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
    return regionRepository.findRegionByCriteria(criteria, pageable).map(IRegionMapper.INSTANCE::toDto);
  }

  @Override
  public RegionDTO findById(Long id) {
    return IRegionMapper.INSTANCE.toDto(regionRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Region not found on id: " + id)));
  }

  @Transactional(readOnly = false)
  @Override
  public RegionDTO saveRegion(RegionDTO regionDTO) {

    if (regionDTO.getId() != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New region expected. Identifier %s got", regionDTO.getId()));
    }
    Region region = IRegionMapper.INSTANCE.toEntity(regionDTO);
    region = regionRepository.save(region);

    return IRegionMapper.INSTANCE.toDto(region);
  }

  @Transactional(readOnly = false)
  @Override
  public RegionDTO updateRegion(Long id, RegionDTO regionDTO) {

    Region region = regionRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Region not found on id: " + id));

    IRegionMapper.INSTANCE.updateFromDto(regionDTO, region);
    region = regionRepository.save(region);

    return IRegionMapper.INSTANCE.toDto(region);

  }

  @Transactional(readOnly = false)
  @Override
  public void deleteRegion(Long id) {

    Region regionBD = regionRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Region not found on id: " + id));
    regionRepository.deleteById(id);

  }

}
