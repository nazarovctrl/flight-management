package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.User;
import uz.ccrew.flightmanagement.enums.UserRole;
import uz.ccrew.flightmanagement.exp.NotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private Long USER_ID;

    @BeforeEach
    void setUp() {
        User user = User.builder().
                login("Azimjon")
                .password("200622az")
                .cashbackAmount(0L)
                .role(UserRole.CUSTOMER)
                .credentialsModifiedDate(LocalDateTime.now())
                .build();
        userRepository.save(user);
        USER_ID = user.getId();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void load() {
        assertDoesNotThrow(() -> userRepository.loadById(USER_ID));
    }

    @Test
    void loadThrows() {
        assertThrows(NotFoundException.class, () -> userRepository.loadById(123123123L));
    }

    @Test
    void findByLogin() {
        String login = "Azimjon";
        Optional<User> optional = userRepository.findByLogin(login);
        assertTrue(optional.isPresent());

        User result = optional.get();
        assertEquals(login, result.getLogin());
    }
}