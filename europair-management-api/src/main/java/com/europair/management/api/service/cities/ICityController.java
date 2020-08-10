package com.europair.management.api.service.cities;


import com.europair.management.api.dto.cities.dto.CityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/cities")
public interface ICityController {

    /**
     * <p>
     *     Retrieves a paginated list of cities.
     * </p>
     * @param pageable - pagination info
     * @return paginated list of cities.
     */

    @GetMapping("")
    public ResponseEntity<Page<CityDTO>> getAllCitiesPaginated(final Pageable pageable) ;

    /**
     * <p>
     *     Retrieves city data identified by id.
     * </p>
     * @param id - Unique identifier of the city.
     * @return City data.
     */

    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> getCityById(@PathVariable final Long id) ;

    /**
     * <p>
     *     Creates new city.
     * </p>
     * @param cityDTO - City data
     * @return New city data.
     */

    @PostMapping("")
    public ResponseEntity<CityDTO> saveCity(@RequestBody final CityDTO cityDTO) ;

    /**
     * <p>
     *     Updates city data identified by id.
     * </p>
     * @param id - Unique identifier of the city.
     * @param cityDTO - City data.
     * @return Updated city data.
     */

    @PutMapping("/{id}")
    public ResponseEntity<CityDTO> updateCity(@PathVariable final Long id, @RequestBody final CityDTO cityDTO) ;

    /**
     * <p>
     *     Removes city data identified by id.
     * </p>
     * @param id - Unique identifier of the city.
     * @return
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable final Long id) ;

}
