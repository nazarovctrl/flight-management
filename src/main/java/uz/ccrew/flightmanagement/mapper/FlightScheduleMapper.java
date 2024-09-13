package uz.ccrew.flightmanagement.mapper;

import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleCreateDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleDTO;
import uz.ccrew.flightmanagement.entity.FlightSchedule;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FlightScheduleMapper implements Mapper<FlightScheduleCreateDTO, FlightScheduleDTO, FlightSchedule> {

    @Override
    public FlightSchedule toEntity(FlightScheduleCreateDTO flightScheduleCreateDTO) {
        return FlightSchedule.builder()
                .airlineCode(flightScheduleCreateDTO.airlineCode())
                .usualAircraftTypeCode(flightScheduleCreateDTO.usualAircraftTypeCode())
                .departureDateTime(flightScheduleCreateDTO.departureDateTime())
                .arrivalDateTime(flightScheduleCreateDTO.arrivalDateTime())
                .build();
    }

    @Override
    public FlightScheduleDTO toDTO(FlightSchedule flightSchedule) {
        return FlightScheduleDTO.builder()
                .flightNumber(flightSchedule.getFlightNumber())
                .airlineCode(flightSchedule.getAirlineCode())
                .usualAircraftTypeCode(flightSchedule.getUsualAircraftTypeCode())
                .originAirportCode(flightSchedule.getOriginAirport().getAirportCode())
                .destinationAirportCode(flightSchedule.getDestinationAirport().getAirportCode())
                .departureDateTime(flightSchedule.getDepartureDateTime())
                .arrivalDateTime(flightSchedule.getArrivalDateTime())
                .build();
    }
}
