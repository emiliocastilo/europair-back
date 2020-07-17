package com.europair.management.rest.users.service;

import com.europair.management.rest.model.tasks.dto.TaskDTO;
import com.europair.management.rest.model.users.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

  Page<UserDTO> findAllPaginated(Pageable pageable);
  UserDTO findById(Long id);
  UserDTO saveUser(UserDTO userDTO);
  UserDTO updateUser(Long id, UserDTO userDTO);
  void deleteUser(Long id);

}
