package uz.ccrew.flightmanagement.dto.leg;

import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Update flight leg")
public record LegUpdateDTO(@NotNull(message = "actual departure time can not be null")
                           LocalDateTime actualDepartureTime,
                           @NotNull(message = "actual arrival time can not be null")
                           LocalDateTime actualArrivalTime) {}
