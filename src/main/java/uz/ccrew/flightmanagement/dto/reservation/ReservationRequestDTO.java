package uz.ccrew.flightmanagement.dto.reservation;

import uz.ccrew.flightmanagement.enums.TravelClassCode;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ReservationRequestDTO(
                                    String departureCity,
                                    String arrivalCity,
                                    LocalDate departureDate,
                                    LocalDate returnDate,
                                    TravelClassCode travelClassCode) {
}
