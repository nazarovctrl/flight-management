package uz.ccrew.flightmanagement.util;

import uz.ccrew.flightmanagement.entity.FlightSchedule;
import uz.ccrew.flightmanagement.enums.TravelClassCode;
import uz.ccrew.flightmanagement.service.OneWayFlightService;
import uz.ccrew.flightmanagement.dto.flightSchedule.OneWayFlightDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.TravelClassAggregationDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class FlightUtil {
    private final OneWayFlightService oneWayFlightService;

    public TravelClassAggregationDTO getTravelClassAggregation(List<FlightSchedule> flights) {
        Set<TravelClassCode> commonClassCost = null;
        Set<TravelClassCode> commonClassSeats = null;
        HashMap<TravelClassCode, Long> combinedClassCostMap = new HashMap<>();
        HashMap<TravelClassCode, Integer> combinedClassSeatsMap = new HashMap<>();

        for (FlightSchedule flight : flights) {
            Optional<OneWayFlightDTO> oneWayFlightOptional = oneWayFlightService.getOneWayFlight(flight);
            if (oneWayFlightOptional.isEmpty()) {
                return null;
            }
            OneWayFlightDTO oneWayFlight = oneWayFlightOptional.get();

            combinedClassCostMap = processCostIntersections(oneWayFlight.travelClassCostList(), commonClassCost, combinedClassCostMap);
            combinedClassSeatsMap = processSeatIntersections(oneWayFlight.travelClassAvailableSeats(), commonClassSeats, combinedClassSeatsMap);
            commonClassCost = combinedClassCostMap.keySet();
            commonClassSeats = combinedClassSeatsMap.keySet();

            if (commonClassCost.isEmpty() || commonClassSeats.isEmpty()) {
                return null;
            }
        }

        return TravelClassAggregationDTO.builder()
                .classCost(combinedClassCostMap)
                .classSeats(combinedClassSeatsMap)
                .build();
    }

    public HashMap<TravelClassCode, Integer> processSeatIntersections(HashMap<TravelClassCode, Integer> classMap, Set<TravelClassCode> set, HashMap<TravelClassCode, Integer> map) {
        Set<TravelClassCode> travelClassCodes = classMap.keySet();

        if (set == null) {
            set = new HashSet<>(travelClassCodes);
        } else {
            set.retainAll(travelClassCodes);
        }

        for (TravelClassCode travelClassCode : set) {
            Integer seatCount = classMap.get(travelClassCode);
            map.compute(travelClassCode, (key, old) -> {
                if (old == null) {
                    return seatCount;
                }
                return Math.min(seatCount, old);
            });
        }
        return map;
    }

    public HashMap<TravelClassCode, Long> processCostIntersections(HashMap<TravelClassCode, Long> classMap, Set<TravelClassCode> set, HashMap<TravelClassCode, Long> map) {
        Set<TravelClassCode> travelClassCodes = classMap.keySet();

        if (set == null) {
            set = new HashSet<>(travelClassCodes);
        } else {
            set.retainAll(travelClassCodes);
        }

        for (TravelClassCode travelClassCode : set) {
            Long cost = classMap.get(travelClassCode);
            map.compute(travelClassCode, (key, old) -> {
                if (old == null) {
                    return cost;
                }
                return cost + old;
            });
        }

        return map;
    }
}
