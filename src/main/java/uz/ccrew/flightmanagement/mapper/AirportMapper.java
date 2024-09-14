package uz.ccrew.flightmanagement.mapper;

import uz.ccrew.flightmanagement.entity.Airport;
import uz.ccrew.flightmanagement.dto.airport.AirportDTO;
import uz.ccrew.flightmanagement.dto.airport.AirportCreateDTO;

import org.springframework.stereotype.Component;

@Component
public class AirportMapper implements Mapper<AirportCreateDTO, AirportDTO, Airport> {

    @Override
    public Airport toEntity(AirportCreateDTO dto) {
        return Airport.builder()
                .airportCode(dto.airportCode())
                .airportName(dto.airportName())
                .airportLocation(dto.airportLocation())
                .otherDetails(dto.otherDetails())
                .city(dto.city())
                .build();
    }

    @Override
    public AirportDTO toDTO (Airport airport) {
        return AirportDTO.builder()
                .airportCode(airport.getAirportCode())
                .airportName(airport.getAirportName())
                .airportLocation(airport.getAirportLocation())
                .otherDetails(airport.getOtherDetails())
                .city(airport.getCity())
                .build();
    }
}
