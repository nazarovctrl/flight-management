package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.dto.passenger.PassengerDTO;

import org.springframework.data.domain.Page;

public interface ReportService {
    Page<PassengerDTO> findPassengersWithReservedSeatsOnFlight(String flightNumber, int page, int size);

    Long calculateTotalSalesByFlightNumber(Long flightNumber);
}
