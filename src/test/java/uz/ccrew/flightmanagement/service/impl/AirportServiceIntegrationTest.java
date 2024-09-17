package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.dto.airport.AirportCreateDTO;
import uz.ccrew.flightmanagement.dto.airport.AirportDTO;
import uz.ccrew.flightmanagement.service.AirportService;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ActiveProfiles("test")
public class AirportServiceIntegrationTest {

    @Autowired
    private AirportService airportService;

    @Test
    public void testAddAirportIntegration() {
        AirportCreateDTO airportCreateDTO = AirportCreateDTO.builder()
                .airportCode("LAX")
                .airportName("Los Angeles International")
                .airportLocation("USA Los Angeles")
                .city("Los Angeles")
                .build();

        AirportDTO result = airportService.addAirport(airportCreateDTO);

        assertNotNull(result);
        assertEquals("LAX", result.airportCode());
    }

    @Test
    public void testGetCityListIntegration() {
        List<String> cities = airportService.getCityList();

        assertNotNull(cities);
        assertFalse(cities.isEmpty());
    }
}
