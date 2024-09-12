package uz.ccrew.flightmanagement.dto.airport;

import lombok.Builder;

@Builder
public record AirportDTO(String airportCode,
                         String airportName,
                         String airportLocation,
                         String otherDetails) {}
