package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.service.ReportService;
import uz.ccrew.flightmanagement.repository.ItineraryLegRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ItineraryLegRepository itineraryLegRepository;

    @Override
    public Long calculateTotalSalesByFlightNumber(Long flightNumber) {
        return itineraryLegRepository.calculateTotalSalesByFlightNumber(flightNumber);
    }
}
