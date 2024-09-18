package uz.ccrew.flightmanagement.dto.flightSchedule;

import lombok.Builder;
import uz.ccrew.flightmanagement.enums.TravelClassCode;

import java.util.HashMap;
import java.util.List;

@Builder
public record MultiCityFlightDTO(List<FlightScheduleDTO> flights,
                                 HashMap<TravelClassCode, Long> travelClassCostList,
                                 HashMap<TravelClassCode, Integer> travelClassAvailableSeats) {
}
