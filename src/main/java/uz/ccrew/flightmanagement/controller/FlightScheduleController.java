package uz.ccrew.flightmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleCreateDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleDTO;
import uz.ccrew.flightmanagement.service.FlightScheduleService;
import uz.ccrew.flightmanagement.dto.Response;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/flightSchedule")
@RequiredArgsConstructor
@Tag(name = "FlightSchedule Controller", description = "FlightSchedule API")
@SecurityRequirement(name = "Bearer Authentication")
public class FlightScheduleController {
    private final FlightScheduleService flightScheduleService;

    @PostMapping("/add")
    @Operation(summary = "Add flightSchedule, role admin")
    public ResponseEntity<FlightScheduleDTO> add(@RequestBody @Valid FlightScheduleCreateDTO flightScheduleCreateDTO) {
        FlightScheduleDTO result = flightScheduleService.addFlightSchedule(flightScheduleCreateDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete flightSchedule, role admin")
    public ResponseEntity<Response<?>> delete(@PathVariable("id") Long id) {
        flightScheduleService.delete(id);
        return ResponseMaker.okMessage("FlightSchedule deleted");
    }
}
