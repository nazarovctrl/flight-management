package uz.ccrew.flightmanagement.dto.auth;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for Login")
public record LoginDTO(@NotBlank(message = "login must be not blank.")
                       @Schema(description = "login", example = "john")
                       String login,
                       @NotBlank(message = "password must be not blank.")
                       @Schema(description = "password", example = "12345")
                       String password) {
}