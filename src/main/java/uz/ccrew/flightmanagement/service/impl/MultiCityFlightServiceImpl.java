package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.dto.flightSchedule.OneWayFlightDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.TravelClassAggregationDTO;
import uz.ccrew.flightmanagement.util.FlightUtil;
import uz.ccrew.flightmanagement.entity.FlightSchedule;
import uz.ccrew.flightmanagement.enums.TravelClassCode;
import uz.ccrew.flightmanagement.repository.LegRepository;
import uz.ccrew.flightmanagement.mapper.FlightScheduleMapper;
import uz.ccrew.flightmanagement.service.OneWayFlightService;
import uz.ccrew.flightmanagement.service.MultiCityFlightService;
import uz.ccrew.flightmanagement.repository.FlightScheduleRepository;
import uz.ccrew.flightmanagement.dto.flightSchedule.MultiCityFlightDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightListRequestDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MultiCityFlightServiceImpl implements MultiCityFlightService {
    private final FlightUtil flightUtil;
    private final LegRepository legRepository;
    private final FlightScheduleMapper flightMapper;
    private final OneWayFlightService oneWayFlightService;
    private final FlightScheduleRepository flightRepository;

    @Override
    public List<MultiCityFlightDTO> getMultiCityFlights(FlightListRequestDTO dto) {
        List<List<FlightSchedule>> possibleRoutes = new ArrayList<>();
        findRoutes(dto.departureCity(), dto.arrivalCity(), new ArrayList<>(), possibleRoutes, new HashSet<>(), dto.maxStops());

        return possibleRoutes.parallelStream()
                .map(this::getMultiCityFlight)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Override
    public Optional<MultiCityFlightDTO> getMultiCityFlight(List<FlightSchedule> flights) {
        TravelClassAggregationDTO travelClassAggregation = flightUtil.getTravelClassAggregation(flights);

        if (travelClassAggregation == null) {
            return Optional.empty();
        }

        MultiCityFlightDTO multiCityFlight = MultiCityFlightDTO.builder()
                .flights(flightMapper.toDTOList(flights))
                .travelClassCostList(travelClassAggregation.classCost())
                .travelClassAvailableSeats(travelClassAggregation.classSeats())
                .build();
        return Optional.of(multiCityFlight);
    }

    private void findRoutes(String currentCity, String finalCity, List<FlightSchedule> currentRoute,
                            List<List<FlightSchedule>> possibleRoutes, Set<String> visitedCity, int remainingStops) {
        if (remainingStops < 0) {
            return;
        }

        visitedCity.add(currentCity);

        LocalDateTime previousArrival = null;
        if (!currentRoute.isEmpty()) {
            previousArrival = currentRoute.getLast().getArrivalDateTime();
        }

        List<FlightSchedule> nextFlights;
        if (previousArrival != null) {
            nextFlights = flightRepository.findByOriginAirportCityAndTimeConstraints(currentCity, previousArrival.plusHours(1), previousArrival.plusHours(2));
        } else {
            nextFlights = flightRepository.findByOriginAirportCity(currentCity);
        }

        for (FlightSchedule flight : nextFlights) {
            int legCount = legRepository.countByFlightSchedule_FlightNumber(flight.getFlightNumber());

            if (visitedCity.contains(flight.getDestinationAirport().getCity())) {
                continue;
            }

            List<FlightSchedule> newRoute = new ArrayList<>(currentRoute);
            newRoute.add(flight);

            if (flight.getDestinationAirport().getCity().equals(finalCity)) {
                possibleRoutes.add(newRoute);
            } else {
                findRoutes(flight.getDestinationAirport().getCity(), finalCity, newRoute, possibleRoutes, new HashSet<>(visitedCity), remainingStops - legCount);
            }
        }
    }
}
