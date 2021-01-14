package com.europair.management.api.service.users;

import com.europair.management.api.dto.users.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@RequestMapping(value = {"/users", "/external/users"})
public interface IUserController {


    /**
     * <p>
     * Retrieves a paginated list of Users filtered by properties criteria.
     * </p>
     *
     * @param pageable pagination info
     * @param reqParam Map of filter params, values and operators. (pe: plateNumber=JKL,CONTAINS)
     * @return Paginated list of users
     */
    @GetMapping
    @Operation(description = "Paged result of users with advanced filter by property", security = {@SecurityRequirement(name = "bearerAuth")})
    ResponseEntity<Page<UserDTO>> getAllUsersPaginatedByFilter(
            @Parameter(description = "Pagination filter") final Pageable pageable,
            @Parameter(description = "Map of properties to filter with value and operator, (pe: username=a,CONTAINS)") @RequestParam Map<String, String> reqParam);

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable final Long id) ;

    @PostMapping("")
    public ResponseEntity<UserDTO> saveUser(@RequestBody final UserDTO userDTO) ;

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable final Long id, @RequestBody final UserDTO userDTO) ;

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable final Long id) ;

}
