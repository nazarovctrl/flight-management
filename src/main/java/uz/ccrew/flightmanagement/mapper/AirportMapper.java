package uz.ccrew.flightmanagement.mapper;

import uz.ccrew.flightmanagement.dto.airport.AirportCreateDTO;
import uz.ccrew.flightmanagement.dto.airport.AirportDTO;
import uz.ccrew.flightmanagement.entity.Airport;

import org.springframework.stereotype.Component;

@Component
public class AirportMapper implements Mapper<AirportCreateDTO, AirportDTO, Airport>{

    @Override
    public Airport toEntity(AirportCreateDTO airportCreateDTO) {
        return Airport.builder()
                .airportCode(airportCreateDTO.airportCode())
                .airportName(airportCreateDTO.airportName())
                .airportLocation(airportCreateDTO.airportLocation())
                .otherDetails(airportCreateDTO.otherDetails())
                .build();
    }

    @Override
    public AirportDTO toDTO(Airport airport) {
        return AirportDTO.builder()
                .airportCode(airport.getAirportCode())
                .airportName(airport.getAirportName())
                .airportLocation(airport.getAirportLocation())
                .otherDetails(airport.getOtherDetails())
                .build();
    }
}
