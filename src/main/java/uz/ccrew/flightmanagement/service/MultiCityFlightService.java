package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.entity.FlightSchedule;
import uz.ccrew.flightmanagement.dto.flightSchedule.MultiCityFlightDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightListRequestDTO;

import java.util.List;
import java.util.Optional;

public interface MultiCityFlightService {
    List<MultiCityFlightDTO> getMultiCityFlights(FlightListRequestDTO dto);

    Optional<MultiCityFlightDTO> getMultiCityFlight(List<FlightSchedule> flights);
}