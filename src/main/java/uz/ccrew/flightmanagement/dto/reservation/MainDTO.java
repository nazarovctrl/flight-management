package uz.ccrew.flightmanagement.dto.reservation;

import uz.ccrew.flightmanagement.enums.TicketTypeCode;
import uz.ccrew.flightmanagement.enums.TravelClassCode;
import uz.ccrew.flightmanagement.dto.passenger.PassengerCreateDTO;

import lombok.Builder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


@Builder
public record MainDTO(@NotNull(message = "Invalid ticket type code")
                      TicketTypeCode ticketTypeCode,
                      @NotNull(message = "Invalid travel class code")
                      TravelClassCode travelClassCode,
                      @Valid
                      PassengerCreateDTO passenger,
                      @NotNull(message = "Invalid booking agent id")
                      Integer bookingAgentId,
                      @NotNull(message = "Invalid use cashback value")
                      Boolean useCashback) {
}
