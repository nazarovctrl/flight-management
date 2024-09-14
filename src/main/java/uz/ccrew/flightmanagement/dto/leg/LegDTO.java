package uz.ccrew.flightmanagement.dto.leg;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LegDTO(Long legId,
                     Long flightNumber,
                     String originAirport,
                     String destinationAirport,
                     LocalDateTime actualDepartureTime,
                     LocalDateTime actualArrivalTime) {
}
