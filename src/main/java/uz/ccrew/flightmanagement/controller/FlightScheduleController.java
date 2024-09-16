package uz.ccrew.flightmanagement.controller;

import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.service.FlightScheduleService;
import uz.ccrew.flightmanagement.dto.flightSchedule.OneWayFlightDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.RoundTripFlightDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightListRequestDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleCreateDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleReportDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.time.LocalDate;
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

    @GetMapping("/get-by-airport/{code}")
    @Operation(summary = "Get all flights for a given airport.")
    public ResponseEntity<Response<Page<FlightScheduleDTO>>> getFlightsByAirport(@PathVariable("code") String code,
                                                                                 @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                                 @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Page<FlightScheduleDTO> result = flightScheduleService.getAllFlightSchedulesByAirportCode(code, page, size);
        return ResponseMaker.ok(result);
    }

    @GetMapping("/list/on-time")
    @Operation(summary = "Get all flights on time")
    public ResponseEntity<Response<Page<FlightScheduleReportDTO>>> getOnTimeFlights(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                                    @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Page<FlightScheduleReportDTO> result = flightScheduleService.getOnTimeFlights(page, size);
        return ResponseMaker.ok(result);
    }

    @GetMapping("/list/delayed")
    @Operation(summary = "Get all flights delayed")
    public ResponseEntity<Response<Page<FlightScheduleReportDTO>>> getDelayedFlights(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                                     @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Page<FlightScheduleReportDTO> result = flightScheduleService.getDelayedFlights(page, size);
        return ResponseMaker.ok(result);
    }

    @GetMapping("/list/one-way")
    @Operation(summary = "Get list flights for one-way")
    public ResponseEntity<Response<List<OneWayFlightDTO>>> getOneWayList(@RequestParam("departureCity") String departureCity,
                                                                         @RequestParam("arrivalCity") String arrivalCity,
                                                                         @RequestParam("departureDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate) {
        FlightListRequestDTO flightListRequestDTO = FlightListRequestDTO.builder()
                .departureCity(departureCity)
                .arrivalCity(arrivalCity)
                .departureDate(departureDate).build();

        List<OneWayFlightDTO> result = flightScheduleService.getOneWayList(flightListRequestDTO);
        return ResponseMaker.ok(result);
    }

    @GetMapping("/list/round-trip")
    @Operation(summary = "Get list flights for round trip")
    public ResponseEntity<Response<List<RoundTripFlightDTO>>> getRoundTripList(@RequestParam("departureCity") String departureCity,
                                                                               @RequestParam("arrivalCity") String arrivalCity,
                                                                               @RequestParam("departureDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
                                                                               @RequestParam("returnDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate) {
        FlightListRequestDTO flightListRequestDTO = FlightListRequestDTO.builder()
                .departureCity(departureCity)
                .arrivalCity(arrivalCity)
                .departureDate(departureDate)
                .returnDate(returnDate).build();

        List<RoundTripFlightDTO> result = flightScheduleService.getRoundTripList(flightListRequestDTO);
        return ResponseMaker.ok(result);
    }
}
