package uz.ccrew.flightmanagement.dto.leg;

import lombok.Builder;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Builder
@Schema(description = "Request body for add flight leg")
public record LegCreateDTO(Long flightNumber,
                           String originAirport,
                           String destinationAirport,
                           LocalDateTime actualDepartureTime,
                           LocalDateTime actualArrivalTime) {
}
