package uz.ccrew.flightmanagement.service;

import org.springframework.data.domain.Page;
import uz.ccrew.flightmanagement.dto.passenger.PassengerDTO;

public interface ReportService {
    Page<PassengerDTO> findPassengersWithReservedSeatsOnFlight(String flightNumber, int page, int size);

    Long calculateTotalSalesByFlightNumber(Long flightNumber);
}
