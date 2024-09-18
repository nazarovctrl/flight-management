package uz.ccrew.flightmanagement.security.user;

import uz.ccrew.flightmanagement.service.AuthService;
import uz.ccrew.flightmanagement.dto.auth.RegisterDTO;
import uz.ccrew.flightmanagement.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserDetailsServiceImplTest {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;

    void register(String login) {
        RegisterDTO registerDTO = new RegisterDTO(login, "200622az");
        authService.register(registerDTO);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void loadUserByUsername() {
        String login = "Azimjon";
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(login));

        register(login);

        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        assertNotNull(userDetails);
        assertEquals(login.toLowerCase(), userDetails.getUsername());
    }
}