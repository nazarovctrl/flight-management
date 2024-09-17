package uz.ccrew.flightmanagement.dto.flightSchedule;

import lombok.Builder;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Builder
public record FlightScheduleReportDTO (@NotNull(message = "flight number code must not be blank.")
                                       @Schema(description = "flight number", example = "DL")
                                       Long flightNumber,
                                       @NotNull(message = "departure date time must not be blank.")
                                       @Schema(description = "departure date time", example = "2023-09-15T08:30:00")
                                       LocalDateTime departureDateTime,
                                       @NotNull(message = "arrival date time must not be blank.")
                                       @Schema(description = "arrival date time", example = "2023-09-15T08:45:00")
                                       LocalDateTime arrivalDateTime,
                                       @NotNull(message = "actual departure dateTime must not be blank.")
                                       @Schema(description = "actual departure dateTime", example = "2023-09-15T08:30:00")
                                       LocalDateTime actualDepartureDateTime,
                                       @NotNull(message = "actual arrival dateTime code must not be blank.")
                                       @Schema(description = "actual arrival dateTime", example = "2023-09-15T08:45:00")
                                       LocalDateTime actualArrivalDateTime) {}
