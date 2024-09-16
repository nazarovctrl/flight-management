package uz.ccrew.flightmanagement.dto.reservation;

import uz.ccrew.flightmanagement.enums.TravelClassCode;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleDTO;

import java.util.HashMap;
import java.util.List;

public record FlightReservationDTO(FlightScheduleDTO flightDTO,
                                   FlightScheduleDTO returnFlightDTO,
                                   List<TravelClassCostDTO> travelClassCostList,
                                   HashMap<TravelClassCode, Integer> travelClassAvailableSeats) {

}
