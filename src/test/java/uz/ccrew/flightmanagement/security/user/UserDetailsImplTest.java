package uz.ccrew.flightmanagement.security.user;

import uz.ccrew.flightmanagement.entity.User;
import uz.ccrew.flightmanagement.enums.UserRole;

import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserDetailsImplTest {
    @Mock
    private User mockUser;
    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(mockUser.getLogin()).thenReturn("Azimjon");
        when(mockUser.getPassword()).thenReturn("200622az");
        when(mockUser.getRole()).thenReturn(UserRole.CUSTOMER);

        userDetails = new UserDetailsImpl(mockUser);
    }

    @Test
    void getUser() {
        User user = userDetails.getUser();
        assertNotNull(user);
    }

    @Test
    void getAuthorities() {
        GrantedAuthority grantedAuthority = userDetails.getAuthorities().stream().findFirst().get();
        assertEquals(UserRole.CUSTOMER.name(), grantedAuthority.getAuthority());
    }

    @Test
    void getPassword() {
        assertEquals("200622az", userDetails.getPassword());
    }

    @Test
    void getUsername() {
        assertEquals("Azimjon", userDetails.getUsername());
    }

    @Test
    void isAccountNonExpired() {
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked() {
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired() {
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    void isEnabled() {
        assertTrue(userDetails.isEnabled());
    }
}