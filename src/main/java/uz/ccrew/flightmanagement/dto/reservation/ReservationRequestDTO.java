package uz.ccrew.flightmanagement.dto.reservation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import uz.ccrew.flightmanagement.enums.TravelClassCode;

import java.time.LocalDate;

@Builder
@Schema
public record ReservationRequestDTO(@NotBlank(message = "Invalid departure city")
                                    String departureCity,
                                    @NotBlank(message = "Invalid arrival city")
                                    String arrivalCity,
                                    @NotBlank(message = "Invalid departure date")
                                    LocalDate departureDate,
                                    LocalDate returnDate,
                                    TravelClassCode travelClassCode) {
}
