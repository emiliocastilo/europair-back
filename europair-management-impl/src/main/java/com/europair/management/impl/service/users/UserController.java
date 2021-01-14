package com.europair.management.impl.service.users;

import com.europair.management.api.dto.users.UserDTO;
import com.europair.management.api.service.users.IUserController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements IUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    public ResponseEntity<Page<UserDTO>> getAllUsersPaginatedByFilter(final Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[UserController] - Starting method [getAllUsersPaginatedByFilter] with input: pageable={}, reqParam={}", pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<UserDTO> userDTOPage = userService.findAllPaginatedByFilter(pageable, criteria);
        LOGGER.debug("[UserController] - Ending method [getAllUsersPaginatedByFilter] with return: {}", userDTOPage);
        return ResponseEntity.ok().body(userDTOPage);
    }

    public ResponseEntity<UserDTO> getUserById(@PathVariable final Long id) {
        LOGGER.debug("[UserController] - Starting method [getUserById] with input: id={}", id);
        final UserDTO taskDTO = userService.findById(id);
        LOGGER.debug("[UserController] - Ending method [getUserById] with return: {}", taskDTO);
        return ResponseEntity.ok().body(taskDTO);
    }

    public ResponseEntity<UserDTO> saveUser(@RequestBody final UserDTO userDTO) {
        LOGGER.debug("[UserController] - Starting method [saveUser] with input: userDTO={}", userDTO);
        final UserDTO savedUserDTO = userService.saveUser(userDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUserDTO.getId())
                .toUri();
        LOGGER.debug("[UserController] - Ending method [saveUser] with return: {}", savedUserDTO);
        return ResponseEntity.created(location).body(savedUserDTO);
    }

    public ResponseEntity<UserDTO> updateUser(@PathVariable final Long id, @RequestBody final UserDTO userDTO) {
        LOGGER.debug("[UserController] - Starting method [updateUser] with input: id={}, userDTO={}", id, userDTO);
        final UserDTO savedUserDTO = userService.updateUser(id, userDTO);
        LOGGER.debug("[UserController] - Ending method [updateUser] with return: {}", savedUserDTO);
        return ResponseEntity.ok().body(savedUserDTO);
    }

    public ResponseEntity<?> deleteUser(@PathVariable final Long id) {
        LOGGER.debug("[UserController] - Starting method [deleteUser] with input: id={}", id);
        userService.deleteUser(id);
        LOGGER.debug("[UserController] - Ending method [deleteUser] with no return.");
        return ResponseEntity.noContent().build();
    }

}
