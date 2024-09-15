package uz.ccrew.flightmanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.dto.reservation.OneWayReservationCreateDTO;
import uz.ccrew.flightmanagement.dto.reservation.ReservationDTO;
import uz.ccrew.flightmanagement.service.ReservationService;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
@Tag(name = "Reservation controller", description = "Reservation API")
@SecurityRequirement(name = "Bearer Authentication")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/make/one-way")
    public ResponseEntity<Response<ReservationDTO>> makeOneWay(@RequestBody @Valid OneWayReservationCreateDTO dto) {
        ReservationDTO result = reservationService.makeOneWay(dto);
        return ResponseMaker.ok(result);
    }
}
