package com.europair.management.impl.service.cities;

import com.europair.management.api.dto.cities.CityDTO;
import com.europair.management.api.service.cities.ICityController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(CityController.class);

    private final ICityService cityService;

    @Override
    public ResponseEntity<Page<CityDTO>> getAllCitiesPaginated(final Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[CityController] - Starting method [getAllCitiesPaginated] with input: pageable={}, repParam={}", pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<CityDTO> cityDTOPage = cityService.findAllPaginatedByFilter(pageable, criteria);
        LOGGER.debug("[CityController] - Ending method [getAllCitiesPaginated] with return: {}", cityDTOPage);
        return ResponseEntity.ok().body(cityDTOPage);
    }

    @Override
    public ResponseEntity<CityDTO> getCityById(@NotNull final Long id) {
        LOGGER.debug("[CityController] - Starting method [getCityById] with input: id={}", id);
        final CityDTO cityDTO = cityService.findById(id);
        LOGGER.debug("[CityController] - Ending method [getCityById] with return: {}", cityDTO);
        return ResponseEntity.ok().body(cityDTO);
    }

    @Override
    public ResponseEntity<CityDTO> saveCity(final CityDTO cityDTO) {
        LOGGER.debug("[CityController] - Starting method [saveCity] with input: cityDTO={}", cityDTO);
        final CityDTO savedCityDTO = cityService.saveCity(cityDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCityDTO.getId())
                .toUri();

        LOGGER.debug("[CityController] - Ending method [saveCity] with return: {}", cityDTO);
        return ResponseEntity.created(location).body(savedCityDTO);
    }

    @Override
    public ResponseEntity<CityDTO> updateCity(@NotNull final Long id, final CityDTO cityDTO) {
        LOGGER.debug("[CityController] - Starting method [updateCity] with input: id={}, cityDTO={}", id, cityDTO);
        final CityDTO savedCityDTO = cityService.updateCity(id, cityDTO);
        LOGGER.debug("[CityController] - Ending method [updateCity] with return: {}", cityDTO);
        return ResponseEntity.ok().body(savedCityDTO);
    }

    @Override
    public ResponseEntity<?> deleteCity(@NotNull final Long id) {
        LOGGER.debug("[CityController] - Starting method [deleteCity] with input: id={}", id);
        cityService.deleteCity(id);
        LOGGER.debug("[CityController] - Ending method [deleteCity] with no return.");
        return ResponseEntity.noContent().build();
    }

}
