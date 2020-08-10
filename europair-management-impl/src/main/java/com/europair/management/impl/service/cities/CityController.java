package com.europair.management.impl.service.cities;

import com.europair.management.api.dto.cities.dto.CityDTO;
import com.europair.management.api.service.cities.ICityController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/cities")
public class CityController implements ICityController {

    private final ICityService cityService;

    /**
     * <p>
     *     Retrieves a paginated list of cities.
     * </p>
     * @param pageable - pagination info
     * @return paginated list of cities.
     */

    @GetMapping("")
    @Override
    public ResponseEntity<Page<CityDTO>> getAllCitiesPaginated(final Pageable pageable) {

        final Page<CityDTO> cityDTOPage = cityService.findAllPaginated(pageable);
        return ResponseEntity.ok().body(cityDTOPage);

    }

    /**
     * <p>
     *     Retrieves city data identified by id.
     * </p>
     * @param id - Unique identifier of the city.
     * @return City data.
     */

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<CityDTO> getCityById(@PathVariable final Long id) {

        final CityDTO cityDTO = cityService.findById(id);
        return ResponseEntity.ok().body(cityDTO);

    }

    /**
     * <p>
     *     Creates new city.
     * </p>
     * @param cityDTO - City data
     * @return New city data.
     */

    @PostMapping("")
    @Override
    public ResponseEntity<CityDTO> saveCity(@RequestBody final CityDTO cityDTO) {

        final CityDTO savedCityDTO = cityService.saveCity(cityDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCityDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedCityDTO);

    }

    /**
     * <p>
     *     Updates city data identified by id.
     * </p>
     * @param id - Unique identifier of the city.
     * @param cityDTO - City data.
     * @return Updated city data.
     */

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<CityDTO> updateCity(@PathVariable final Long id, @RequestBody final CityDTO cityDTO) {

        final CityDTO savedCityDTO = cityService.updateCity(id, cityDTO);

        return ResponseEntity.ok().body(savedCityDTO);

    }

    /**
     * <p>
     *     Removes city data identified by id.
     * </p>
     * @param id - Unique identifier of the city.
     * @return
     */

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> deleteCity(@PathVariable final Long id) {

        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();

    }

}
