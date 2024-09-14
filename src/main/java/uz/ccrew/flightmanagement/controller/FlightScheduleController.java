package uz.ccrew.flightmanagement.controller;

import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.service.FlightScheduleService;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleDTO;
import uz.ccrew.flightmanagement.dto.reservation.ReservationRequestDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleCreateDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

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

    @GetMapping("/list/one-way")
    @Operation(summary = "Get list flights for one-way")
    public ResponseEntity<Response<List<FlightScheduleDTO>>> getOneWayList(@RequestBody @Valid ReservationRequestDTO dto,
                                                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                                                           @RequestParam(value = "page", defaultValue = "0") int page) {
        List<FlightScheduleDTO> result = flightScheduleService.getOneWayList(dto, page, size);
        return ResponseMaker.ok(result);
    }
}
