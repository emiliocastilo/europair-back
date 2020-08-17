package com.europair.management.impl.service.users;

import com.europair.management.api.dto.users.UserDTO;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.users.UserMapper;
import com.europair.management.rest.model.users.entity.User;
import com.europair.management.rest.model.users.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Page<UserDTO> findAllPaginated(Pageable pageable) {
        return userRepository.findAll(pageable).map(user -> UserMapper.INSTANCE.toDto(user));
    }

    @Override
    public UserDTO findById(Long id) {
        return UserMapper.INSTANCE.toDto(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on id: " + id)));
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.toEntity(userDTO);

        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user = userRepository.save(user);

        return UserMapper.INSTANCE.toDto(user);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on id: " + id));
        if (!user.getPassword().equals(userDTO.getPassword())) {
            userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        }

        UserMapper.INSTANCE.updateFromDto(userDTO, user);
        user = userRepository.save(user);

        return UserMapper.INSTANCE.toDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        User userBD = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on id: " + id));
        userRepository.deleteById(id);
    }

    private UserDTO updateUserValues(Long id, UserDTO userDTO) {

        return UserDTO.builder()
                .id(id)
                .username(userDTO.getUsername())
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .email(userDTO.getEmail())
                .timeZone(userDTO.getTimeZone())
                .roles(userDTO.getRoles())
                .tasks(userDTO.getTasks())
                .build();

    }
}
