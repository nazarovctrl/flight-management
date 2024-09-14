package uz.ccrew.flightmanagement.dto.airport;

import lombok.Builder;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Builder
@Schema(description = "Request body for Airport")
public record AirportCreateDTO(@NotBlank(message = "airport code must be not blank.")
                               @Schema(description = "airport code", example = "JFK")
                               String airportCode,
                               @NotBlank(message = "airport name must be not blank.")
                               @Schema(description = "airport name", example = "John F. Kennedy")
                               String airportName,
                               @NotBlank(message = "airport location must be not blank.")
                               @Schema(description = "airport location", example = "New York")
                               String airportLocation,
                               @Schema(description = "Airport details", example = "{\"timezone\": \"GMT+3\", \"runways\": 2, \"terminals\": [\"A\", \"B\"], \"annualPassengers\": 30000000}")
                               String otherDetails) {
}
