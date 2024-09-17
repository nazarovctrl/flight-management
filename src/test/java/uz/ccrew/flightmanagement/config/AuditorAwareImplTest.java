package uz.ccrew.flightmanagement.config;

import uz.ccrew.flightmanagement.service.AuthService;
import uz.ccrew.flightmanagement.dto.auth.RegisterDTO;
import uz.ccrew.flightmanagement.repository.UserRepository;
import uz.ccrew.flightmanagement.security.user.UserDetailsServiceImpl;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AuditorAwareImplTest {
    @Autowired
    private AuditorAwareImpl auditorAware;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;

    void register(String login) {
        RegisterDTO registerDTO = new RegisterDTO(login, "200622az");
        authService.register(registerDTO);
    }

    void setAuthentication(String login) {
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(login);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void getCurrentAuditor() {
        Optional<Long> currentAuditor = auditorAware.getCurrentAuditor();
        assertTrue(currentAuditor.isEmpty());

        String login = "Azimjon";
        register(login);
        setAuthentication(login);

        currentAuditor = auditorAware.getCurrentAuditor();
        assertTrue(currentAuditor.isPresent());

        Long id = currentAuditor.get();
        assertNotNull(id);

        SecurityContextHolder.getContext().setAuthentication(null);
        userRepository.deleteAll();
    }
}