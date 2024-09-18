package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.entity.Leg;
import uz.ccrew.flightmanagement.entity.ItineraryLeg;
import uz.ccrew.flightmanagement.repository.LegRepository;
import uz.ccrew.flightmanagement.service.ItineraryLegService;
import uz.ccrew.flightmanagement.entity.ItineraryReservation;
import uz.ccrew.flightmanagement.repository.ItineraryLegRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItineraryLegServiceImpl implements ItineraryLegService {
    private final LegRepository legRepository;
    private final ItineraryLegRepository itineraryLegRepository;

    @Override
    public void addItineraryLegs(ItineraryReservation reservation, Long... flightNumbers) {
        List<ItineraryLeg> itineraryLegList = new ArrayList<>();
        for (Long flightNumber : flightNumbers) {
            List<Leg> legs = legRepository.findAllByFlightSchedule_FlightNumber(flightNumber);
            for (Leg leg : legs) {
                ItineraryLeg itineraryLeg = ItineraryLeg.builder()
                        .id(new ItineraryLeg.ItineraryLegId(reservation.getReservationId(), leg.getLegId()))
                        .leg(leg)
                        .reservation(reservation)
                        .build();
                itineraryLegList.add(itineraryLeg);
            }
        }
        itineraryLegRepository.saveAll(itineraryLegList);
    }
}
