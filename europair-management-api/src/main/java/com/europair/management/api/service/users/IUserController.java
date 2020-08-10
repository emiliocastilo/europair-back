package com.europair.management.api.service.users;

import com.europair.management.api.dto.users.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/users")
public interface IUserController {


    @GetMapping("")
    public ResponseEntity<Page<UserDTO>> getAllUsersPaginated(final Pageable pageable) ;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable final Long id) ;

    @PostMapping("")
    public ResponseEntity<UserDTO> saveUser(@RequestBody final UserDTO userDTO) ;

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable final Long id, @RequestBody final UserDTO userDTO) ;

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable final Long id) ;

}
