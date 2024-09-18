package uz.ccrew.flightmanagement.dto.reservation;

import lombok.Builder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Builder
@Schema(description = "Request body for make one way reservation")
public record OneWayReservationCreateDTO(@NotNull(message = "Invalid flight number")
                                         Long flightNumber,
                                         @NotNull(message = "Main info can not be null")
                                         @Valid
                                         MainDTO main) {
}
