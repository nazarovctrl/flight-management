package uz.ccrew.flightmanagement.dto.reservation;

import uz.ccrew.flightmanagement.enums.TravelClassCode;

import lombok.Builder;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Builder
@Schema
public record ReservationRequestDTO(@NotBlank(message = "Invalid departure city")
                                    String departureCity,
                                    @NotBlank(message = "Invalid arrival city")
                                    String arrivalCity,
                                    @NotNull(message = "Invalid departure date")
                                    LocalDate departureDate,
                                    LocalDate returnDate,
                                    TravelClassCode travelClassCode) {
}
