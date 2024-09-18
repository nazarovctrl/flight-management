package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.entity.User;
import uz.ccrew.flightmanagement.util.AuthUtil;
import uz.ccrew.flightmanagement.dto.user.UserDTO;
import uz.ccrew.flightmanagement.mapper.UserMapper;
import uz.ccrew.flightmanagement.service.UserService;
import uz.ccrew.flightmanagement.dto.user.UserUpdateDTO;
import uz.ccrew.flightmanagement.exp.AlreadyExistException;
import uz.ccrew.flightmanagement.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthUtil authUtil;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO get() {
        User user = authUtil.loadLoggedUser();
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO getById(Long userId) {
        User user = userRepository.loadById(userId);
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO update(UserUpdateDTO dto) {
        User user = authUtil.loadLoggedUser();

        dto = dto.withRole(user.getRole());

        update(user, dto);

        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO updateById(Long userId, UserUpdateDTO dto) {
        User user = userRepository.loadById(userId);

        update(user, dto);

        return userMapper.toDTO(user);
    }

    @Override
    public void deleteById(Long userId) {
        User user = userRepository.loadById(userId);
        userRepository.delete(user);
    }

    @Override
    public Page<UserDTO> getList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<User> pageObj = userRepository.findAll(pageable);

        List<User> userList = pageObj.getContent();
        List<UserDTO> dtoList = userList.stream().map(userMapper::toDTO).toList();

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }

    private void update(User user, UserUpdateDTO dto) {
        boolean different = false;

        if (dto.login() != null && !user.getLogin().equals(dto.login())) {
            Optional<User> optional = userRepository.findByLogin(dto.login());
            if (optional.isPresent()) {
                throw new AlreadyExistException("Login is already existing");
            }
            user.setLogin(dto.login());
            different = true;
        }

        if (dto.password() != null) {
            String password = passwordEncoder.encode(dto.password());
            if (!user.getPassword().equals(password)) {
                user.setPassword(password);
                different = true;
            }
        }

        if (dto.role() != null && !user.getRole().equals(dto.role())) {
            user.setRole(dto.role());
        }

        if (different) {
            user.setCredentialsModifiedDate(LocalDateTime.now());
        }
        userRepository.save(user);
    }
}