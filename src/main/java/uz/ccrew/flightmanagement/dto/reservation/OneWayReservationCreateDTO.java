package uz.ccrew.flightmanagement.dto.reservation;

import uz.ccrew.flightmanagement.enums.TicketTypeCode;
import uz.ccrew.flightmanagement.enums.TravelClassCode;

import lombok.Builder;
import jakarta.validation.constraints.NotNull;

@Builder
public record OneWayReservationCreateDTO(@NotNull(message = "Invalid flight number")
                                         Long flightNumber,
                                         @NotNull(message = "Invalid ticket type code")
                                         TicketTypeCode ticketTypeCode,
                                         @NotNull(message = "Invalid travel class code")
                                         TravelClassCode travelClassCode) {
}
