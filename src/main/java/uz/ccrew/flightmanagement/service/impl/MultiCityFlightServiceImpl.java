package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.entity.FlightSchedule;
import uz.ccrew.flightmanagement.enums.TravelClassCode;
import uz.ccrew.flightmanagement.repository.LegRepository;
import uz.ccrew.flightmanagement.mapper.FlightScheduleMapper;
import uz.ccrew.flightmanagement.service.OneWayFlightService;
import uz.ccrew.flightmanagement.service.MultiCityFlightService;
import uz.ccrew.flightmanagement.dto.flightSchedule.OneWayFlightDTO;
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
    private final LegRepository legRepository;
    private final FlightScheduleMapper flightMapper;
    private final OneWayFlightService oneWayFlightService;
    private final FlightScheduleRepository flightRepository;

    @Override
    public List<MultiCityFlightDTO> getMultiCityFlights(FlightListRequestDTO dto) {
        List<List<FlightSchedule>> possibleRoutes = new ArrayList<>();
        findRoutes(dto.departureCity(), dto.arrivalCity(), new ArrayList<>(), possibleRoutes, new HashSet<>(), dto.maxStops());

        return possibleRoutes.parallelStream()
                .map(this::getMultiCityDTO)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
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

    public Optional<MultiCityFlightDTO> getMultiCityDTO(List<FlightSchedule> flights) {
        Set<TravelClassCode> commonClassCost = null;
        Set<TravelClassCode> commonClassAvailableSeats = null;
        HashMap<TravelClassCode, Long> combinedClassCost = new HashMap<>();
        HashMap<TravelClassCode, Integer> combinedClassAvailableSeats = new HashMap<>();

        for (FlightSchedule flight : flights) {
            Optional<OneWayFlightDTO> oneWayFlightOptional = oneWayFlightService.getOneWayFlight(flight);
            if (oneWayFlightOptional.isEmpty()) {
                return Optional.empty();
            }

            OneWayFlightDTO oneWayFlight = oneWayFlightOptional.get();

            Set<TravelClassCode> travelClassCodesForCost = oneWayFlight.travelClassCostList().keySet();
            Set<TravelClassCode> travelClassCodesForAvailableSeats = oneWayFlight.travelClassAvailableSeats().keySet();

            if (commonClassCost == null) {
                commonClassCost = new HashSet<>(travelClassCodesForCost);
            } else {
                commonClassCost.retainAll(travelClassCodesForCost);
            }

            if (commonClassAvailableSeats == null) {
                commonClassAvailableSeats = new HashSet<>(travelClassCodesForAvailableSeats);
            } else {
                commonClassAvailableSeats.retainAll(travelClassCodesForAvailableSeats);
            }

            if (commonClassCost.isEmpty() || commonClassAvailableSeats.isEmpty()) {
                return Optional.empty();
            }

            for (TravelClassCode travelClassCode : commonClassCost) {
                Long cost = oneWayFlight.travelClassCostList().get(travelClassCode);
                combinedClassCost.put(travelClassCode, cost);
            }
            for (TravelClassCode travelClassCode : commonClassAvailableSeats) {
                Integer availableSeats = oneWayFlight.travelClassAvailableSeats().get(travelClassCode);
                combinedClassAvailableSeats.put(travelClassCode, availableSeats);
            }
        }

        if (commonClassCost == null) {
            return Optional.empty();
        }

        MultiCityFlightDTO multiCityFlight = MultiCityFlightDTO.builder()
                .flights(flightMapper.toDTOList(flights))
                .travelClassCostList(combinedClassCost)
                .travelClassAvailableSeats(combinedClassAvailableSeats)
                .build();

        return Optional.of(multiCityFlight);
    }
}
