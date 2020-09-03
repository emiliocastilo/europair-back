package com.europair.management.impl.service.cities;

import com.europair.management.api.dto.cities.CityDTO;
import com.europair.management.api.service.cities.ICityController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CityController implements ICityController {

    private final ICityService cityService;

    @Override
    public ResponseEntity<Page<CityDTO>> getAllCitiesPaginated(final Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<CityDTO> cityDTOPage = cityService.findAllPaginatedByFilter(pageable, criteria);
        return ResponseEntity.ok().body(cityDTOPage);

    }

    @Override
    public ResponseEntity<CityDTO> getCityById(@NotNull final Long id) {
        final CityDTO cityDTO = cityService.findById(id);
        return ResponseEntity.ok().body(cityDTO);
    }

    @Override
    public ResponseEntity<CityDTO> saveCity(final CityDTO cityDTO) {
        final CityDTO savedCityDTO = cityService.saveCity(cityDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCityDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedCityDTO);
    }

    @Override
    public ResponseEntity<CityDTO> updateCity(@NotNull final Long id, final CityDTO cityDTO) {
        final CityDTO savedCityDTO = cityService.updateCity(id, cityDTO);
        return ResponseEntity.ok().body(savedCityDTO);
    }

    @Override
    public ResponseEntity<?> deleteCity(@NotNull final Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }

}
