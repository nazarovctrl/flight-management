package uz.ccrew.flightmanagement.dto.reservation;

import lombok.Builder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema
@Builder
public record RoundTripReservationCreate(@NotNull(message = "Invalid flight number")
                                         Long flightNumber,
                                         @NotNull(message = "Invalid return flight number")
                                         Long returnFlightNumber,
                                         @NotNull(message = "Main info can not be null")
                                         @Valid
                                         MainDTO main) {
}
