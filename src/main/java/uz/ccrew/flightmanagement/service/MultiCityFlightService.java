package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.dto.flightSchedule.MultiCityFlightDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightListRequestDTO;

import java.util.List;

public interface MultiCityFlightService {
    List<MultiCityFlightDTO> getMultiCityFlights(FlightListRequestDTO dto);
}
