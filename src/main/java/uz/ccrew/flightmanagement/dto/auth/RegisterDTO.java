package uz.ccrew.flightmanagement.dto.auth;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for Registration")
public record RegisterDTO(@NotBlank(message = "Login must not be blank")
                          @Schema(description = "login", example = "john")
                          String login,
                          @NotBlank(message = "Login must not be blank")
                          @Schema(description = "password", example = "12345")
                          String password) {
}