package uz.ccrew.flightmanagement.controller;

import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.service.ReservationService;
import uz.ccrew.flightmanagement.dto.reservation.ReservationDTO;
import uz.ccrew.flightmanagement.dto.reservation.OneWayReservationCreateDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
@Tag(name = "Reservation controller", description = "Reservation API")
@SecurityRequirement(name = "Bearer Authentication")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/make/one-way")
    @Operation(summary = "Make one way reservation")
    public ResponseEntity<Response<ReservationDTO>> makeOneWay(@RequestBody @Valid OneWayReservationCreateDTO dto) {
        ReservationDTO result = reservationService.makeOneWay(dto);
        return ResponseMaker.ok(result);
    }

    @PostMapping("/cancel/{reservationId}")
    @Operation(summary = "Cancel reservation")
    public ResponseEntity<Response<ReservationDTO>> cancel(@PathVariable("reservationId") Long reservationId) {
        ReservationDTO result = reservationService.cancel(reservationId);
        return ResponseMaker.ok(result);
    }

    @GetMapping("/list")
    @Operation(summary = "Get reservation list")
    public ResponseEntity<Response<Page<ReservationDTO>>> getList(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                  @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Page<ReservationDTO> result = reservationService.getList(page, size);
        return ResponseMaker.ok(result);
    }
}
