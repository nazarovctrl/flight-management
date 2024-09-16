package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.dto.flightSchedule.*;

import org.springframework.data.domain.Page;
import uz.ccrew.flightmanagement.entity.FlightSchedule;

import java.util.List;
import java.util.Optional;

public interface FlightScheduleService {
    FlightScheduleDTO addFlightSchedule(FlightScheduleCreateDTO flightScheduleCreateDTO);

    void delete(Long flightNumber);

    FlightScheduleDTO getFlightSchedule(Long flightNumber);

    Page<FlightScheduleDTO> getAllFlightSchedulesByAirportCode(String airportCode, int page, int size);

    Page<FlightScheduleReportDTO> getOnTimeFlights(int page, int size);

    Page<FlightScheduleReportDTO> getDelayedFlights(int page, int size);

    List<OneWayFlightDTO> getOneWayList(FlightListRequestDTO dto);

    Optional<OneWayFlightDTO> getOneWayFlight(FlightSchedule flight);

    List<RoundTripFlightDTO> getRoundTripList(FlightListRequestDTO flightListRequestDTO);

    Optional<RoundTripFlightDTO> getRoundTripDTO(RoundTrip roundTrip);
}
