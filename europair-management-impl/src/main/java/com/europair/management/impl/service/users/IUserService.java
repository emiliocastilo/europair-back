package com.europair.management.impl.service.users;


import com.europair.management.api.dto.users.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {

  Page<UserDTO> findAllPaginated(Pageable pageable);
  UserDTO findById(Long id);
  UserDTO saveUser(UserDTO userDTO);
  UserDTO updateUser(Long id, UserDTO userDTO);
  void deleteUser(Long id);

}
