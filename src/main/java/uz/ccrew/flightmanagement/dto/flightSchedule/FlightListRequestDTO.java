package uz.ccrew.flightmanagement.dto.flightSchedule;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record FlightListRequestDTO(String departureCity,
                                   String arrivalCity,
                                   LocalDate departureDate,
                                   LocalDate returnDate) {
}
