package uz.ccrew.flightmanagement.controller;

import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.dto.airport.AirportDTO;
import uz.ccrew.flightmanagement.service.AirportService;
import uz.ccrew.flightmanagement.dto.airport.AirportCreateDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

@RestController
@RequestMapping("/api/v1/airport")
@RequiredArgsConstructor
@Tag(name = "Airport Controller", description = "Airport API")
@SecurityRequirement(name = "Bearer Authentication")
public class AirportController {
    private final AirportService airportService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Add airport, role admin")
    public ResponseEntity<Response<AirportDTO>> addAirport(@RequestBody @Valid AirportCreateDTO airportCreateDTO) {
        AirportDTO result = airportService.addAirport(airportCreateDTO);
        return ResponseMaker.ok(result);
    }

    @GetMapping("/city/list")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR','CUSTOMER')")
    @Operation(summary = "Get city list")
    public ResponseEntity<Response<List<String>>> getCityList() {
        List<String> result = airportService.getCityList();
        return ResponseMaker.ok(result);
    }
}
