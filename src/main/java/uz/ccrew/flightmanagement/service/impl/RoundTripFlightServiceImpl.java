package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.util.FlightUtil;
import uz.ccrew.flightmanagement.dto.flightSchedule.*;
import uz.ccrew.flightmanagement.mapper.FlightScheduleMapper;
import uz.ccrew.flightmanagement.service.RoundTripFlightService;
import uz.ccrew.flightmanagement.repository.FlightScheduleRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RoundTripFlightServiceImpl implements RoundTripFlightService {
    private final FlightUtil flightUtil;
    private final FlightScheduleRepository flightRepository;
    private final FlightScheduleMapper flightScheduleMapper;

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
        TravelClassAggregationDTO travelClassAggregation = flightUtil.getTravelClassAggregation(List.of(roundTrip.flight(), roundTrip.returnFlight()));
        if (travelClassAggregation == null) {
            return Optional.empty();
        }

        RoundTripFlightDTO roundTripFlightDTO = RoundTripFlightDTO.builder()
                .flightDTO(flightScheduleMapper.toDTO(roundTrip.flight()))
                .returnFlightDTO(flightScheduleMapper.toDTO(roundTrip.returnFlight()))
                .travelClassCostList(travelClassAggregation.classCost())
                .travelClassAvailableSeats(travelClassAggregation.classSeats())
                .build();
        return Optional.of(roundTripFlightDTO);
    }
}
