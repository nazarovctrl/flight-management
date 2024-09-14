package uz.ccrew.flightmanagement.mapper;

import uz.ccrew.flightmanagement.entity.Leg;
import uz.ccrew.flightmanagement.dto.leg.LegDTO;
import uz.ccrew.flightmanagement.dto.leg.LegCreateDTO;

import org.springframework.stereotype.Component;

@Component
public class LegMapper implements Mapper<LegCreateDTO, LegDTO, Leg> {
    @Override
    public Leg toEntity(LegCreateDTO dto) {
        return Leg.builder()
                .originAirport(dto.originAirport())
                .destinationAirport(dto.destinationAirport())
                .actualDepartureTime(dto.actualDepartureTime())
                .actualArrivalTime(dto.actualDepartureTime())
                .build();
    }

    @Override
    public LegDTO toDTO(Leg leg) {
        return LegDTO.builder()
                .legId(leg.getLegId())
                .flightNumber(leg.getFlightSchedule().getFlightNumber())
                .originAirport(leg.getOriginAirport())
                .destinationAirport(leg.getDestinationAirport())
                .actualDepartureTime(leg.getActualDepartureTime())
                .actualArrivalTime(leg.getActualArrivalTime())
                .build();
    }
}
