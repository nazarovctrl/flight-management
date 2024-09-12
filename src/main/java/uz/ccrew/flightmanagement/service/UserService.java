package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.dto.user.UserDTO;
import uz.ccrew.flightmanagement.dto.user.UserUpdateDTO;

import org.springframework.data.domain.Page;

public interface UserService {
    UserDTO get();

    UserDTO getById(Integer userId);

    UserDTO update(UserUpdateDTO dto);

    UserDTO updateById(Integer userId, UserUpdateDTO dto);

    void deleteById(Integer userId);

    Page<UserDTO> getList(int page, int size);
}