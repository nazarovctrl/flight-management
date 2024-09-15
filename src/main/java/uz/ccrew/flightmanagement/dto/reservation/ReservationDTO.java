package uz.ccrew.flightmanagement.dto.reservation;

import lombok.Builder;
import uz.ccrew.flightmanagement.dto.passenger.PassengerDTO;
import uz.ccrew.flightmanagement.entity.BookingAgent;
import uz.ccrew.flightmanagement.enums.ReservationStatusCode;
import uz.ccrew.flightmanagement.enums.TicketTypeCode;
import uz.ccrew.flightmanagement.enums.TravelClassCode;

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
