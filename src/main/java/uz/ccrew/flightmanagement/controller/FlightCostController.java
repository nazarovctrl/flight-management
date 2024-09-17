package uz.ccrew.flightmanagement.controller;

import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.service.FlightCostService;
import uz.ccrew.flightmanagement.dto.flightcost.FlightCostDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/flight-cost")
@RequiredArgsConstructor
@Tag(name = "Flight Cost Controller", description = "Flight Cost API")
@SecurityRequirement(name = "Bearer Authentication")
public class FlightCostController {
    private final FlightCostService flightCostService;

    @PostMapping("/save")
    @Operation(summary = "Save flight cost")
    public ResponseEntity<Response<FlightCostDTO>> save(@RequestBody @Valid FlightCostDTO dto) {
        FlightCostDTO result = flightCostService.save(dto);
        return ResponseMaker.ok(result);
    }
}
