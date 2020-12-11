package com.europair.management.impl.service.regions;

import com.europair.management.api.dto.regions.RegionDTO;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRegionService {

  Page<RegionDTO> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);
  RegionDTO findById(Long id);
  RegionDTO saveRegion(RegionDTO regionDTO);
  RegionDTO updateRegion(Long id, RegionDTO regionDTO);
  void deleteRegion(Long id);

}
