package uz.ccrew.flightmanagement.dto.flightSchedule;

import uz.ccrew.flightmanagement.enums.TravelClassCode;

import lombok.Builder;

import java.util.HashMap;


@Builder
public record TravelClassAggregationDTO(HashMap<TravelClassCode, Long> classCost,
                                        HashMap<TravelClassCode, Integer> classSeats) {
}
