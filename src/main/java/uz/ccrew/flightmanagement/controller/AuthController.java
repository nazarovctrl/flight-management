package uz.ccrew.flightmanagement.controller;

import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.user.UserDTO;
import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.dto.auth.LoginDTO;
import uz.ccrew.flightmanagement.service.AuthService;
import uz.ccrew.flightmanagement.dto.auth.RegisterDTO;
import uz.ccrew.flightmanagement.dto.auth.LoginResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "Authorization API")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register Customer")
    public ResponseEntity<Response<UserDTO>> registerDTO(@RequestBody @Valid RegisterDTO dto) {
        return ResponseMaker.ok(authService.register(dto));
    }

    @PostMapping("/login")
    @Operation(summary = "Login User")
    public ResponseEntity<Response<LoginResponseDTO>> login(@RequestBody @Valid LoginDTO loginRequest) {
        return ResponseMaker.ok(authService.login(loginRequest));
    }

    @PostMapping("/refresh")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Refresh Access token")
    public ResponseEntity<Response<String>> refresh() {
        return ResponseMaker.ok(authService.refresh());
    }
}