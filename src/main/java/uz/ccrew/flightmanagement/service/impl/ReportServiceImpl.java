package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.entity.Passenger;
import uz.ccrew.flightmanagement.service.ReportService;
import uz.ccrew.flightmanagement.mapper.PassengerMapper;
import uz.ccrew.flightmanagement.dto.passenger.PassengerDTO;
import uz.ccrew.flightmanagement.repository.ReservationRepository;
import uz.ccrew.flightmanagement.repository.ItineraryLegRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final PassengerMapper passengerMapper;
    private final ReservationRepository reservationRepository;
    private final ItineraryLegRepository itineraryLegRepository;

    @Override
    public Page<PassengerDTO> findPassengersWithReservedSeatsOnFlight(String flightNumber, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Passenger> pageObj = reservationRepository.findPassengersWithReservedSeatsOnFlight(flightNumber, pageable);
        List<PassengerDTO> dtoList = passengerMapper.toDTOList(pageObj.getContent());

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }

    @Override
    public Long calculateTotalSalesByFlightNumber(Long flightNumber) {
        return itineraryLegRepository.calculateTotalSalesByFlightNumber(flightNumber);
    }
}
