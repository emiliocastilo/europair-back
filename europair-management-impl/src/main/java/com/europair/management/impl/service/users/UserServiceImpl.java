package com.europair.management.impl.service.users;

import com.europair.management.api.dto.users.UserDTO;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.mappers.users.UserMapper;
import com.europair.management.impl.util.Utils;
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
        return userRepository.findAll(pageable).map(UserMapper.INSTANCE::toDto);
    }

    @Override
    public UserDTO findById(Long id) {
        return UserMapper.INSTANCE.toDto(userRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.USER_NOT_FOUND, String.valueOf(id))));
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
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.USER_NOT_FOUND, String.valueOf(id)));
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
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.USER_NOT_FOUND, String.valueOf(id)));
        userRepository.deleteById(id);
    }

}
