package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.dto.flightSchedule.OneWayFlightDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.RoundTripFlightDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightListRequestDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleCreateDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleReportDTO;

import org.springframework.data.domain.Page;

import java.util.List;

public interface FlightScheduleService {
    FlightScheduleDTO addFlightSchedule(FlightScheduleCreateDTO flightScheduleCreateDTO);

    void delete(Long flightNumber);

    FlightScheduleDTO getFlightSchedule(Long flightNumber);

    Page<FlightScheduleDTO> getAllFlightSchedulesByAirportCode(String airportCode, int page, int size);

    Page<FlightScheduleReportDTO> getOnTimeFlights(int page, int size);

    Page<FlightScheduleReportDTO> getDelayedFlights(int page, int size);

    List<OneWayFlightDTO> getOneWayList(FlightListRequestDTO dto);

    List<RoundTripFlightDTO> getRoundTripList(FlightListRequestDTO flightListRequestDTO);
}
