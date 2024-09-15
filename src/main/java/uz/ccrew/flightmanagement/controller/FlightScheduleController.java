package uz.ccrew.flightmanagement.controller;

import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleCreateDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleDTO;
import uz.ccrew.flightmanagement.service.FlightScheduleService;
import uz.ccrew.flightmanagement.dto.Response;

import org.springframework.data.domain.Page;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/flight-schedule")
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

    @GetMapping("/get/{id}")
    @Operation(summary = "Get flightSchedule")
    public ResponseEntity<FlightScheduleDTO> get(@PathVariable("id") Long id) {
        FlightScheduleDTO result = flightScheduleService.getFlightSchedule(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get-by-airport/{code}")
    @Operation(summary = "Get all flights for a given airport.")
    public ResponseEntity<Response<Page<FlightScheduleDTO>>> getFlightsByAirport(@PathVariable("code") String code,
                                                                                 @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                                 @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Page<FlightScheduleDTO> result = flightScheduleService.getAllFlightSchedulesByAirportCode(code, page, size);
        return ResponseMaker.ok(result);
    }

    @GetMapping("/list/on-time")
    @Operation(summary = "Get all flights on time and delayed")
    public ResponseEntity<Response<Page<FlightScheduleDTO>>> getFlightsOnTime(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                              @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Page<FlightScheduleDTO> result = flightScheduleService.findSchedulesOnTime(page, size);
        return ResponseMaker.ok(result);
    }

    @GetMapping("/list/delayed")
    @Operation(summary = "Get all flights on time and delayed")
    public ResponseEntity<Response<Page<FlightScheduleDTO>>> getFlightsDelayed(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                               @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Page<FlightScheduleDTO> result = flightScheduleService.findSchedulesDelayed(page, size);
        return ResponseMaker.ok(result);
    }
}
