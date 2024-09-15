package uz.ccrew.flightmanagement.controller;

import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.dto.passenger.PassengerDTO;
import uz.ccrew.flightmanagement.service.PassengerService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
@Tag(name = "Reservation controller", description = "Reservation API")
@SecurityRequirement(name = "Bearer Authentication")
public class ReservationController {

    private final PassengerService passengerService;

    @GetMapping("/find-reserved/seats/{number}")
    @Operation(summary = "Get all customers who have seats reserved on a given flight.")
    public ResponseEntity<Response<Page<PassengerDTO>>> findReservedSeats(@PathVariable("number") String flightNumber,
                                                                          @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                          @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Page<PassengerDTO> result = passengerService.findPassengersWithReservedSeatsOnFlight(flightNumber, page, size);
        return ResponseMaker.ok(result);
    }
}
