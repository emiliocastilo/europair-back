package com.europair.management.impl.service.users.controller;

import com.europair.management.api.dto.users.dto.UserDTO;
import com.europair.management.api.service.users.controller.IUserController;


import com.europair.management.impl.service.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController implements IUserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<Page<UserDTO>> getAllUsersPaginated(final Pageable pageable) {

        final Page<UserDTO> userDTOPage = userService.findAllPaginated(pageable);
        return ResponseEntity.ok().body(userDTOPage);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable final Long id) {
        final UserDTO taskDTO = userService.findById(id);
        return ResponseEntity.ok().body(taskDTO);
    }

    @PostMapping("")
    public ResponseEntity<UserDTO> saveUser(@RequestBody final UserDTO userDTO) {

        final UserDTO savedUserDTO = userService.saveUser(userDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUserDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedUserDTO);

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable final Long id, @RequestBody final UserDTO userDTO) {

        final UserDTO savedUserDTO = userService.updateUser(id, userDTO);

        return ResponseEntity.ok().body(savedUserDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable final Long id) {

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();

    }

}
