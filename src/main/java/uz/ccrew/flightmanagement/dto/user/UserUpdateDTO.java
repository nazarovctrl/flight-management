package uz.ccrew.flightmanagement.dto.user;

import uz.ccrew.flightmanagement.enums.UserRole;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for update")
public record UserUpdateDTO(@Schema(description = "login", example = "john")
                            String login,
                            @Schema(description = "password", example = "12345")
                            String password,
                            @Schema(description = "role", example = "ADMINISTRATOR")
                            UserRole role) {
    public UserUpdateDTO withRole(UserRole role) {
        return new UserUpdateDTO(login(), password(), role);
    }
}