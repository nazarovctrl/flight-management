package uz.ccrew.flightmanagement.dto.reservation;

import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleDTO;
import uz.ccrew.flightmanagement.enums.TravelClassCode;

import java.util.HashMap;
import java.util.List;

public record FlightReservationDTO(FlightScheduleDTO flightScheduleDTO,
                                   List<TravelClassCostDTO> travelClassCostList,
                                   HashMap<TravelClassCode,Integer> travelClassAvailableSeats) {

}
