package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.entity.FlightSchedule;
import uz.ccrew.flightmanagement.dto.flightSchedule.OneWayFlightDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightListRequestDTO;

import java.util.List;
import java.util.Optional;

public interface OneWayFlightService {
    Optional<OneWayFlightDTO> getOneWayFlight(FlightSchedule flight);

    List<OneWayFlightDTO> getOneWayFlights(FlightListRequestDTO dto);
}
