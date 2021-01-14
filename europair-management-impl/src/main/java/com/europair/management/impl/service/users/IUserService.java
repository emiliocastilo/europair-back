package com.europair.management.impl.service.users;


import com.europair.management.api.dto.users.UserDTO;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {

  Page<UserDTO> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);
  UserDTO findById(Long id);
  UserDTO saveUser(UserDTO userDTO);
  UserDTO updateUser(Long id, UserDTO userDTO);
  void deleteUser(Long id);

}
