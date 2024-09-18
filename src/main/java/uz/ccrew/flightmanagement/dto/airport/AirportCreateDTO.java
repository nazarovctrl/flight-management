package uz.ccrew.flightmanagement.dto.airport;

import lombok.Builder;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Builder
@Schema(description = "Request body for Airport")
public record AirportCreateDTO(@NotBlank(message = "Airport code must be not blank.")
                               @Schema(description = "airport code", example = "JFK")
                               String airportCode,
                               @NotBlank(message = "Airport name must be not blank.")
                               @Schema(description = "airport name", example = "John F. Kennedy")
                               String airportName,
                               @NotBlank(message = "Airport location must be not blank.")
                               @Schema(description = "airport location", example = "(40.6413, -73.7781)")
                               String airportLocation,
                               @Schema(description = "Airport details", example = "{\"timezone\": \"GMT+3\", \"runways\": 2, \"terminals\": [\"A\", \"B\"], \"annualPassengers\": 30000000}")
                               String otherDetails,
                               @NotBlank(message = "City must be not blank")
                               @Schema(description = "City", example = "Tashkent")
                               String city) {
}
