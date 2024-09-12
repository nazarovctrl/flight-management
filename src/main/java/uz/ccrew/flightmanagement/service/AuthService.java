package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.dto.user.UserDTO;
import uz.ccrew.flightmanagement.dto.auth.LoginDTO;
import uz.ccrew.flightmanagement.dto.auth.RegisterDTO;
import uz.ccrew.flightmanagement.dto.auth.LoginResponseDTO;

public interface AuthService {
    UserDTO register(RegisterDTO dto);

    LoginResponseDTO login(LoginDTO loginRequest);

    String refresh();
}