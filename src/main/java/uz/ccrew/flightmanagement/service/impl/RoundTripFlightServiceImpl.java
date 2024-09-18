package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.enums.TravelClassCode;
import uz.ccrew.flightmanagement.service.OneWayFlightService;
import uz.ccrew.flightmanagement.dto.flightSchedule.RoundTrip;
import uz.ccrew.flightmanagement.service.RoundTripFlightService;
import uz.ccrew.flightmanagement.dto.flightSchedule.OneWayFlightDTO;
import uz.ccrew.flightmanagement.repository.FlightScheduleRepository;
import uz.ccrew.flightmanagement.dto.flightSchedule.RoundTripFlightDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightListRequestDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoundTripFlightServiceImpl implements RoundTripFlightService {
    private final OneWayFlightService oneWayFlightService;
    private final FlightScheduleRepository flightRepository;

    @Override
    public List<RoundTripFlightDTO> getRoundTripFlights(FlightListRequestDTO dto) {
        List<RoundTrip> roundTrips = flightRepository.findRoundTrip(dto.departureCity(), dto.arrivalCity(), dto.departureDate(), dto.returnDate());

        return roundTrips.parallelStream()
                .map(this::getRoundTrip)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Override
    public Optional<RoundTripFlightDTO> getRoundTrip(RoundTrip roundTrip) {
        Optional<OneWayFlightDTO> flightOptional = oneWayFlightService.getOneWayFlight(roundTrip.flight());
        if (flightOptional.isEmpty()) {
            return Optional.empty();
        }
        Optional<OneWayFlightDTO> retunrFlightOptional = oneWayFlightService.getOneWayFlight(roundTrip.returnFlight());
        if (retunrFlightOptional.isEmpty()) {
            return Optional.empty();
        }
        OneWayFlightDTO flight = flightOptional.get();
        OneWayFlightDTO returnFlight = retunrFlightOptional.get();

        HashMap<TravelClassCode, Long> roundTripCosts = getRoundTripCost(flight.travelClassCostList(), returnFlight.travelClassCostList());
        if (roundTripCosts.isEmpty()) {
            return Optional.empty();
        }

        HashMap<TravelClassCode, Integer> availableSeats = getAvailableSeats(flight.travelClassAvailableSeats(), returnFlight.travelClassAvailableSeats());
        if (availableSeats.isEmpty()) {
            return Optional.empty();
        }

        RoundTripFlightDTO roundTripFlightDTO = RoundTripFlightDTO.builder()
                .flightDTO(flight.flightDTO())
                .returnFlightDTO(returnFlight.flightDTO())
                .travelClassCostList(roundTripCosts)
                .travelClassAvailableSeats(availableSeats)
                .build();
        return Optional.of(roundTripFlightDTO);
    }


    private HashMap<TravelClassCode, Integer> getAvailableSeats(HashMap<TravelClassCode, Integer> flightAvailableSeats, HashMap<TravelClassCode, Integer> returnFlightAvailableSeats) {
        HashMap<TravelClassCode, Integer> availableSeats = new HashMap<>();

        for (Map.Entry<TravelClassCode, Integer> entry : flightAvailableSeats.entrySet()) {
            TravelClassCode travelClassCode = entry.getKey();
            Integer flightAvailableSeat = entry.getValue();

            if (returnFlightAvailableSeats.containsKey(travelClassCode)) {
                Integer returnFlightAvailableSeat = returnFlightAvailableSeats.get(travelClassCode);
                availableSeats.put(travelClassCode, Math.min(flightAvailableSeat, returnFlightAvailableSeat));
            }
        }
        return availableSeats;
    }

    private static HashMap<TravelClassCode, Long> getRoundTripCost(HashMap<TravelClassCode, Long> flightCosts, HashMap<TravelClassCode, Long> returnFlightCosts) {
        HashMap<TravelClassCode, Long> roundTripCosts = new HashMap<>();

        for (Map.Entry<TravelClassCode, Long> entry : flightCosts.entrySet()) {
            TravelClassCode travelClassCode = entry.getKey();
            Long flight1Cost = entry.getValue();

            if (returnFlightCosts.containsKey(travelClassCode)) {
                Long flight2Cost = returnFlightCosts.get(travelClassCode);
                roundTripCosts.put(travelClassCode, flight1Cost + flight2Cost);
            }
        }
        return roundTripCosts;
    }
}
