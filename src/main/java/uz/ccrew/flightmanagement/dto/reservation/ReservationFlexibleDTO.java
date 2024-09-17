package uz.ccrew.flightmanagement.dto.reservation;

import java.time.LocalDateTime;

public record ReservationFlexibleDTO(String departureCity,
                                     String arrivalCity,
                                     LocalDateTime departureTime,
                                     MainDTO mainDTO,
                                     Long payment) {}
