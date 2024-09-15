package uz.ccrew.flightmanagement.dto.reservation;

import uz.ccrew.flightmanagement.enums.TicketTypeCode;
import uz.ccrew.flightmanagement.enums.TravelClassCode;
import uz.ccrew.flightmanagement.dto.passenger.PassengerDTO;
import uz.ccrew.flightmanagement.enums.ReservationStatusCode;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReservationDTO(Long reservationId,
                             BookingAgentDTO agentDTO,
                             PassengerDTO passengerDTO,
                             ReservationStatusCode reservationStatusCode,
                             TicketTypeCode ticketTypeCode,
                             TravelClassCode travelClassCode,
                             LocalDateTime dateReservationMade,
                             Integer numberInParty) {
}
