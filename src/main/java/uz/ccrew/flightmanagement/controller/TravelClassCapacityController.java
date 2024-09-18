package uz.ccrew.flightmanagement.controller;

import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.service.TravelClassCapacityService;
import uz.ccrew.flightmanagement.dto.travelclasscapacity.TravelClassCapacityDTO;
import uz.ccrew.flightmanagement.dto.travelclasscapacity.TravelClassCapacityCreateDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/travel-class-capacity")
@RequiredArgsConstructor
@Tag(name = "Travel Class Capacity Controller", description = "Travel Class Capacity API")
@SecurityRequirement(name = "Bearer Authentication")
public class TravelClassCapacityController {
    private final TravelClassCapacityService travelClassCapacityService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Add travel class capacity")
    public ResponseEntity<Response<TravelClassCapacityDTO>> add(@RequestBody @Valid TravelClassCapacityCreateDTO dto) {
        TravelClassCapacityDTO result = travelClassCapacityService.add(dto);
        return ResponseMaker.ok(result);
    }
}
