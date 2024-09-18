package uz.ccrew.flightmanagement.controller;

import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.dto.reservation.*;
import uz.ccrew.flightmanagement.service.ReservationService;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
@Tag(name = "Reservation controller", description = "Reservation API")
@SecurityRequirement(name = "Bearer Authentication")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/make/one-way")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Make one way reservation")
    public ResponseEntity<Response<ReservationDTO>> makeOneWay(@RequestBody @Valid OneWayReservationCreateDTO dto) {
        ReservationDTO result = reservationService.makeOneWay(dto);
        return ResponseMaker.ok(result);
    }

    @PostMapping("/make/round-trip")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Make round trip reservation")
    public ResponseEntity<Response<ReservationDTO>> makeRoundTrip(@RequestBody @Valid RoundTripReservationCreate dto) {
        ReservationDTO result = reservationService.makeRoundTrip(dto);
        return ResponseMaker.ok(result);
    }

    @PostMapping("/make/flexible")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Make flexible time reservation")
    public ResponseEntity<Response<ReservationDTO>> makeFlexible(@RequestBody @Valid ReservationFlexibleDTO dto) {
        ReservationDTO result = reservationService.makeFlexible(dto);
        return ResponseMaker.ok(result);
    }

    @PostMapping("/make/multi-city")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Make multi city reservation")
    public ResponseEntity<Response<ReservationDTO>> makeMultiCity(@RequestBody @Valid MultiCityReservationCreateDTO dto) {
        ReservationDTO result = reservationService.makeMultiCity(dto);
        return ResponseMaker.ok(result);
    }

    @PostMapping("/cancel/{reservationId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Cancel reservation")
    public ResponseEntity<Response<ReservationDTO>> cancel(@PathVariable("reservationId") Long reservationId) {
        ReservationDTO result = reservationService.cancel(reservationId);
        return ResponseMaker.ok(result);
    }

    @GetMapping("/my/list")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Get reservation list")
    public ResponseEntity<Response<Page<ReservationDTO>>> getList(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                  @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Page<ReservationDTO> result = reservationService.getList(page, size);
        return ResponseMaker.ok(result);
    }

    @GetMapping("/flight-list/{reservationId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Get Reservation flight list")
    public ResponseEntity<Response<List<FlightScheduleDTO>>> getFlightList(@PathVariable("reservationId") Long reservationId) {
        List<FlightScheduleDTO> result = reservationService.getFlightList(reservationId);
        return ResponseMaker.ok(result);
    }
}
