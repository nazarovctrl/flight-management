package uz.ccrew.flightmanagement.dto.reservation;

import uz.ccrew.flightmanagement.enums.AirlineCode;
import uz.ccrew.flightmanagement.enums.AircraftTypeCode;

import java.time.LocalDateTime;

public record ReservationFlexibleDTO(String departureCity,
                                     String arrivalCity,
                                     LocalDateTime departureTime,
                                     LocalDateTime arrivalTime,
                                     AircraftTypeCode aircraftTypeCode,
                                     AirlineCode airlineCode,
                                     MainDTO mainDTO,
                                     Long payment) {}
