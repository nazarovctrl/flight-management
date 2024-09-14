package uz.ccrew.flightmanagement.dto.flightSchedule;

import uz.ccrew.flightmanagement.enums.AirlineCode;
import uz.ccrew.flightmanagement.enums.AircraftTypeCode;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FlightScheduleDTO(Long flightNumber,
                                AirlineCode airlineCode,
                                AircraftTypeCode usualAircraftTypeCode,
                                String originAirportCode,
                                String destinationAirportCode,
                                LocalDateTime departureDateTime,
                                LocalDateTime arrivalDateTime) {
}
