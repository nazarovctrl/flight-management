package uz.ccrew.flightmanagement.dto.flightSchedule;

import uz.ccrew.flightmanagement.enums.AirlineCode;
import uz.ccrew.flightmanagement.enums.AircraftTypeCode;

import lombok.Builder;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Builder
public record FlightScheduleCreateDTO(@NotNull(message = "Airline code must not be blank.")
                                      @Schema(description = "Airline code", example = "DL")
                                      AirlineCode airlineCode,
                                      @NotNull(message = "Aircraft type code must not be blank.")
                                      @Schema(description = "Aircraft type code", example = "B738")
                                      AircraftTypeCode usualAircraftTypeCode,
                                      @NotBlank(message = "Origin airport code must not be blank.")
                                      @Schema(description = "Origin airport code", example = "ATL")
                                      String originAirportCode,
                                      @NotBlank(message = "Destination airport code must not be blank.")
                                      @Schema(description = "Destination airport code", example = "LAX")
                                      String destinationAirportCode,
                                      @NotNull(message = "Departure date time must not be null.")
                                      @Schema(description = "Departure date time", example = "2023-09-15T08:30:00")
                                      LocalDateTime departureDateTime,
                                      @NotNull(message = "Arrival date time must not be null.")
                                      @Schema(description = "Arrival date time", example = "2023-09-15T10:45:00")
                                      LocalDateTime arrivalDateTime) {}