package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.dto.airport.AirportCreateDTO;
import uz.ccrew.flightmanagement.dto.airport.AirportDTO;

import java.util.List;

public interface AirportService {
    AirportDTO addAirport(AirportCreateDTO airportCreateDTO);

    List<String> getCityList();
}
