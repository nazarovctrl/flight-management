package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.repository.*;
import uz.ccrew.flightmanagement.entity.FlightCost;
import uz.ccrew.flightmanagement.entity.FlightSchedule;
import uz.ccrew.flightmanagement.enums.TravelClassCode;
import uz.ccrew.flightmanagement.entity.TravelClassCapacity;
import uz.ccrew.flightmanagement.mapper.FlightScheduleMapper;
import uz.ccrew.flightmanagement.service.OneWayFlightService;
import uz.ccrew.flightmanagement.dto.flightSchedule.OneWayFlightDTO;
import uz.ccrew.flightmanagement.dto.reservation.TravelClassSeatDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightListRequestDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Optional;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OneWayFlightServiceImpl implements OneWayFlightService {
    private final LegRepository legRepository;
    private final FlightScheduleMapper flightMapper;
    private final FlightScheduleRepository flightRepository;
    private final FlightCostRepository flightCostRepository;
    private final ItineraryLegRepository itineraryLegRepository;
    private final TravelClassCapacityRepository travelClassCapacityRepository;

    @Override
    public List<OneWayFlightDTO> getOneWayFlights(FlightListRequestDTO dto) {
        List<FlightSchedule> flightSchedules = flightRepository.findOneWay(dto.departureCity(), dto.arrivalCity(), dto.departureDate());

        return flightSchedules.parallelStream()
                .map(this::getOneWayFlight)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Override
    public Optional<OneWayFlightDTO> getOneWayFlight(FlightSchedule flight) {
        HashMap<TravelClassCode, Integer> totalSeats = new HashMap<>();
        HashMap<TravelClassCode, Long> costs = new HashMap<>();
        Map<TravelClassCode, Integer> reservedSeats = getReservedSeats(flight.getFlightNumber());

        initializeCostAndTotalSeats(flight.getFlightNumber(), costs, totalSeats);

        HashMap<TravelClassCode, Integer> availableSeats = computeAvailableSeats(totalSeats, reservedSeats);
        if (availableSeats.isEmpty()) {
            return Optional.empty();
        }
        OneWayFlightDTO flightDTO = OneWayFlightDTO.builder()
                .flightDTO(flightMapper.toDTO(flight))
                .travelClassCostList(costs)
                .travelClassAvailableSeats(availableSeats)
                .build();
        return Optional.of(flightDTO);
    }

    private void initializeCostAndTotalSeats(Long flightNumber, Map<TravelClassCode, Long> costs, HashMap<TravelClassCode, Integer> totalSeats) {
        LocalDate now = LocalDate.now();
        List<FlightCost> flightCosts = flightCostRepository.findByFlightSchedule_FlightNumberAndId_ValidFromDateLessThanEqualAndValidToDateGreaterThanEqual(
                flightNumber, now, now);

        // Process flight costs to accumulate total seats and cost DTOs
        for (FlightCost flightCost : flightCosts) {
            List<TravelClassCapacity> travelClassCapacities = travelClassCapacityRepository.findById_AircraftTypeCode(flightCost.getId().getAircraftTypeCode());

            for (TravelClassCapacity capacity : travelClassCapacities) {
                TravelClassCode travelClassCode = capacity.getId().getTravelClassCode();
                totalSeats.merge(travelClassCode, capacity.getSeatCapacity(), Integer::sum);
                costs.put(travelClassCode, flightCost.getFlightCost());
            }
        }
    }

    private Map<TravelClassCode, Integer> getReservedSeats(Long flightNumber) {
        int legCount = legRepository.countByFlightSchedule_FlightNumber(flightNumber);

        List<TravelClassSeatDTO> travelClassSeatList = itineraryLegRepository.getTravelClassReservedSeatsByFlight(flightNumber, legCount);
        return travelClassSeatList.stream().collect(Collectors.toMap(TravelClassSeatDTO::getTravelClassCode, TravelClassSeatDTO::getReservedSeats));
    }

    private HashMap<TravelClassCode, Integer> computeAvailableSeats(HashMap<TravelClassCode, Integer> totalSeats, Map<TravelClassCode, Integer> reservedSeats) {
        HashMap<TravelClassCode, Integer> availableSeats = new HashMap<>();
        for (Map.Entry<TravelClassCode, Integer> entry : totalSeats.entrySet()) {
            TravelClassCode travelClassCode = entry.getKey();
            int total = entry.getValue();
            int reserved = reservedSeats.getOrDefault(travelClassCode, 0);
            int available = total - reserved;
            if (available < 1) {
                continue;
            }
            availableSeats.put(travelClassCode, available);
        }
        return availableSeats;
    }
}
