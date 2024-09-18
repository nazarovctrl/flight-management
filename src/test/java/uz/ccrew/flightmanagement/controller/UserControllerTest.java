package uz.ccrew.flightmanagement.controller;

import uz.ccrew.flightmanagement.entity.User;
import uz.ccrew.flightmanagement.enums.UserRole;
import uz.ccrew.flightmanagement.dto.user.UserDTO;
import uz.ccrew.flightmanagement.dto.auth.LoginDTO;
import uz.ccrew.flightmanagement.service.AuthService;
import uz.ccrew.flightmanagement.dto.auth.RegisterDTO;
import uz.ccrew.flightmanagement.dto.user.UserUpdateDTO;
import uz.ccrew.flightmanagement.dto.auth.LoginResponseDTO;
import uz.ccrew.flightmanagement.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private String ACCESS_TOKEN;
    private Long USER_ID;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();

        RegisterDTO registerDTO = new RegisterDTO("bob", "200622az");
        UserDTO register = authService.register(registerDTO);
        USER_ID = register.id();

        LoginDTO loginDTO = new LoginDTO("bob", "200622az");
        LoginResponseDTO login = authService.login(loginDTO);
        ACCESS_TOKEN = login.accessToken();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void getUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/get")
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.login").value("bob".toLowerCase()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.role").value(UserRole.CUSTOMER.name()));
    }

    @Test
    void getUserByIdWithCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/get/{userId}", USER_ID)
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    void getUserByIdWithAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/get/{userId}", USER_ID)
                .header("Authorization", "Bearer " + getAdminAccessToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.login").value("bob".toLowerCase()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.role").value(UserRole.CUSTOMER.name()));
    }

    @Test
    void updateUser() throws Exception {
        UserUpdateDTO updateDTO = new UserUpdateDTO("Nazarov", "200622Az", UserRole.ADMINISTRATOR);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/user/update")
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.login").value(updateDTO.login().toLowerCase()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.role").value(UserRole.CUSTOMER.name()));
    }

    @Test
    void updateUserByIdWithCustomer() throws Exception {
        UserUpdateDTO updateDTO = new UserUpdateDTO("Nazarov", "200622Az", UserRole.ADMINISTRATOR);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/user/update/{userId}", USER_ID)
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    void updateUserByIdWithAdmin() throws Exception {
        UserUpdateDTO updateDTO = new UserUpdateDTO("Nazarov", "200622Az", UserRole.ADMINISTRATOR);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/user/update/{userId}", USER_ID)
                .header("Authorization", "Bearer " + getAdminAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.login").value(updateDTO.login()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.role").value(updateDTO.role().name()));
    }

    @Test
    void deleteUserByIdWithCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/user/delete/{userId}", USER_ID)
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    void deleteUserByIdWithAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/user/delete/{userId}", USER_ID)
                .header("Authorization", "Bearer " + getAdminAccessToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User deleted"));
    }

    @Test
    void getUserList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/get/list")
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10))
                .header("Authorization", "Bearer " + getAdminAccessToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").exists())
                .andDo(print());
    }

    public String getAdminAccessToken() {
        User user = User.builder()
                .login("admin")
                .password(passwordEncoder.encode("200622az"))
                .cashbackAmount(0L)
                .role(UserRole.ADMINISTRATOR)
                .credentialsModifiedDate(LocalDateTime.now())
                .build();
        userRepository.save(user);

        LoginResponseDTO login = authService.login(new LoginDTO(user.getLogin(), "200622az"));
        return login.accessToken();
    }
}
