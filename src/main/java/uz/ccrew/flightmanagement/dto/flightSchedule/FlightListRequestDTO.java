package uz.ccrew.flightmanagement.dto.flightSchedule;

import uz.ccrew.flightmanagement.enums.TravelClassCode;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record FlightListRequestDTO(String departureCity,
                                   String arrivalCity,
                                   LocalDate departureDate,
                                   LocalDate returnDate,
                                   TravelClassCode travelClassCode) {
}
