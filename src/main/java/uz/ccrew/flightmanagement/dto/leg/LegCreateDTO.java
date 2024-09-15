package uz.ccrew.flightmanagement.dto.leg;

import lombok.Builder;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Builder
@Schema(description = "Request body for add flight leg")
public record LegCreateDTO(@NotNull(message = "Flight number can not be null")
                           Long flightNumber,
                           @NotBlank(message = "Invalid origin airport")
                           String originAirport,
                           @NotBlank(message = "Invalid destination airport")
                           String destinationAirport) {
}
