package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.dto.flightSchedule.RoundTrip;
import uz.ccrew.flightmanagement.dto.flightSchedule.RoundTripFlightDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightListRequestDTO;

import java.util.List;
import java.util.Optional;

public interface RoundTripFlightService {
    List<RoundTripFlightDTO> getRoundTripFlights(FlightListRequestDTO dto);
    Optional<RoundTripFlightDTO> getRoundTripDTO(RoundTrip roundTrip);
}
