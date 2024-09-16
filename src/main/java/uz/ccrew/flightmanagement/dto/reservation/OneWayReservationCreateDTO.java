package uz.ccrew.flightmanagement.dto.reservation;

import jakarta.validation.Valid;
import uz.ccrew.flightmanagement.enums.TicketTypeCode;
import uz.ccrew.flightmanagement.enums.TravelClassCode;
import uz.ccrew.flightmanagement.dto.passenger.PassengerCreateDTO;

import lombok.Builder;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Builder
@Schema(description = "Request body for make one way reservation")
public record OneWayReservationCreateDTO(@NotNull(message = "Invalid flight number")
                                         Long flightNumber,
                                         @NotNull(message = "Invalid ticket type code")
                                         TicketTypeCode ticketTypeCode,
                                         @NotNull(message = "Invalid travel class code")
                                         TravelClassCode travelClassCode,
                                         @Valid
                                         PassengerCreateDTO passenger) {
}
