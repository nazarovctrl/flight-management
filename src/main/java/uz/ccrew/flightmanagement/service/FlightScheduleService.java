package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleCreateDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleDTO;

public interface FlightScheduleService {
    FlightScheduleDTO addFlightSchedule(FlightScheduleCreateDTO flightScheduleCreateDTO);

    void delete(Long flightNumber);

    FlightScheduleDTO getFlightSchedule(Long flightNumber);
}
