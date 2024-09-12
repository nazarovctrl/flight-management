package uz.ccrew.flightmanagement.controller;

import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.airport.AirportCreateDTO;
import uz.ccrew.flightmanagement.dto.airport.AirportDTO;
import uz.ccrew.flightmanagement.service.AirportService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/airport")
@RequiredArgsConstructor
@Tag(name = "Airport Controller", description = "Airport API")
public class AirportController {
    private final AirportService airportService;

    @PostMapping("/addAirport")
    @Operation(summary = "Add airport, role admin")
    public ResponseEntity<Response<AirportDTO>> addAirport(@RequestBody AirportCreateDTO airportCreateDTO) {
        AirportDTO airportDTO = airportService.addAirport(airportCreateDTO);
        return ResponseEntity.ok(new Response<>(airportDTO));
    }
}
