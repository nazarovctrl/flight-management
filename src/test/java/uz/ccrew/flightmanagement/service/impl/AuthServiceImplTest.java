package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.entity.User;
import uz.ccrew.flightmanagement.enums.UserRole;
import uz.ccrew.flightmanagement.dto.user.UserDTO;
import uz.ccrew.flightmanagement.mapper.UserMapper;
import uz.ccrew.flightmanagement.dto.auth.LoginDTO;
import uz.ccrew.flightmanagement.dto.auth.RegisterDTO;
import uz.ccrew.flightmanagement.dto.auth.LoginResponseDTO;
import uz.ccrew.flightmanagement.exp.AlreadyExistException;
import uz.ccrew.flightmanagement.repository.UserRepository;
import uz.ccrew.flightmanagement.security.user.UserDetailsServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AuthServiceImplTest {
    @Autowired
    private AuthServiceImpl authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        User user = User.builder().
                login("azimjon")
                .password(passwordEncoder.encode("200622az"))
                .cashbackAmount(0L)
                .role(UserRole.CUSTOMER)
                .credentialsModifiedDate(LocalDateTime.now())
                .build();
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void register() {
        RegisterDTO registerDTO = new RegisterDTO("Nazarov", "200622az");
        AtomicReference<UserDTO> userDTOAtomic = new AtomicReference<>();
        assertDoesNotThrow(() -> userDTOAtomic.set(authService.register(registerDTO)));

        UserDTO result = userDTOAtomic.get();
        assertNotNull(result.id());
        assertEquals(registerDTO.login().toLowerCase(), result.login());
        assertEquals(UserRole.CUSTOMER, result.role());

        AtomicReference<User> atomicReference = new AtomicReference<>();
        assertDoesNotThrow(() -> atomicReference.set(userRepository.loadById(result.id())));

        User user = atomicReference.get();
        assertNotNull(user.getCredentialsModifiedDate());

        UserDTO userDTO = userMapper.toDTO(user);
        assertEquals(result, userDTO);
    }

    @Test
    void registerWithAlreadyExistLogin() {
        RegisterDTO dto = new RegisterDTO("Azimjon", "200622az");
        assertThrows(AlreadyExistException.class, () -> authService.register(dto));
    }

    @Test
    void login() {
        LoginDTO loginDTO = new LoginDTO("Azimjon", "200622az");
        AtomicReference<LoginResponseDTO> resultAtomic = new AtomicReference<>();
        assertDoesNotThrow(() -> resultAtomic.set(authService.login(loginDTO)));

        LoginResponseDTO result = resultAtomic.get();
        assertNotNull(result);
        assertNotNull(result.accessToken());
        assertNotNull(result.refreshToken());
    }

    @Test
    void loginWithBadCredentials() {
        LoginDTO loginDTO = new LoginDTO("Alex", "200623az");
        assertThrows(BadCredentialsException.class, () -> authService.login(loginDTO));
    }

    @Test
    void refresh() {
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername("Azimjon");
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = authService.refresh();
        assertNotNull(accessToken);

        SecurityContextHolder.getContext().setAuthentication(null);
    }
}