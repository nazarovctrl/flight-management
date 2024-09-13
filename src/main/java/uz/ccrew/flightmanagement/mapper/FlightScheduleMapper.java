package uz.ccrew.flightmanagement.mapper;


import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleCreateDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleDTO;
import uz.ccrew.flightmanagement.entity.Airport;
import uz.ccrew.flightmanagement.entity.FlightSchedule;
import uz.ccrew.flightmanagement.exp.NotFoundException;
import uz.ccrew.flightmanagement.repository.AirportRepository;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class FlightScheduleMapper implements Mapper<FlightScheduleCreateDTO,FlightScheduleDTO, FlightSchedule> {
    private final AirportRepository airportRepository;

    public FlightSchedule toEntity(FlightScheduleCreateDTO flightScheduleCreateDTO) {
        Airport originAirport = airportRepository.findById(flightScheduleCreateDTO.originAirportCode())
                .orElseThrow(() -> new NotFoundException("Origin airport not found"));
        Airport destinationAirport = airportRepository.findById(flightScheduleCreateDTO.destinationAirportCode())
                .orElseThrow(() -> new NotFoundException("Destination airport not found"));

        return FlightSchedule.builder()
                .airlineCode(flightScheduleCreateDTO.airlineCode())
                .usualAircraftTypeCode(flightScheduleCreateDTO.usualAircraftTypeCode())
                .originAirport(originAirport)
                .destinationAirport(destinationAirport)
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
