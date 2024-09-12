package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.dto.airport.AirportCreateDTO;
import uz.ccrew.flightmanagement.dto.airport.AirportDTO;

public interface AirportService {
    AirportDTO addAirport(AirportCreateDTO airportCreateDTO);
}
