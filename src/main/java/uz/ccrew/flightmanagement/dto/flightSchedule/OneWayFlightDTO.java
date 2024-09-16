package uz.ccrew.flightmanagement.dto.flightSchedule;

import uz.ccrew.flightmanagement.enums.TravelClassCode;

import lombok.Builder;

import java.util.HashMap;

@Builder
public record OneWayFlightDTO(FlightScheduleDTO flightDTO,
                              HashMap<TravelClassCode, Long> travelClassCostList,
                              HashMap<TravelClassCode, Integer> travelClassAvailableSeats) {

}
