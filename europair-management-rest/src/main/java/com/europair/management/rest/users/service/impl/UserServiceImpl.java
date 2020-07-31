package com.europair.management.rest.users.service.impl;

import com.europair.management.rest.common.exception.ResourceNotFoundException;
import com.europair.management.rest.model.users.dto.UserDTO;
import com.europair.management.rest.model.users.entity.User;
import com.europair.management.rest.model.users.mapper.UserMapper;
import com.europair.management.rest.users.repository.UserRepository;
import com.europair.management.rest.users.service.UserService;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

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

        String password = generatePassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user = userRepository.save(user);

        return UserMapper.INSTANCE.toDto(user);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on id: " + id));

        //UserDTO dto2Update = updateUserValues(user.getId(), userDTO);
        //dto2Update.setPassword(user.getPassword());

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

    private String generatePassword() {
        //TODO: usar un generador aleatorio. De momento está hardcoded para facilitar el desarrollo.
        return "europ1234";
    }
}
