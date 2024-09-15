package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.dto.passenger.PassengerDTO;
import uz.ccrew.flightmanagement.dto.passenger.PassengerCreateDTO;

import org.springframework.data.domain.Page;

public interface PassengerService {
    PassengerDTO add(PassengerCreateDTO dto);

    Page<PassengerDTO> findPassengersWithReservedSeatsOnFlight(String flightNumber, int page, int size);
}
