package uz.ccrew.flightmanagement.controller;

import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.service.BookingAgentService;
import uz.ccrew.flightmanagement.dto.reservation.BookingAgentDTO;
import uz.ccrew.flightmanagement.dto.bookingagent.BookingAgentCreateDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/booking-agent")
@RequiredArgsConstructor
@Tag(name = "Booking Agent Controller", description = "Booking Agent API")
@SecurityRequirement(name = "Bearer Authentication")
public class BookingAgentController {
    private final BookingAgentService bookingAgentService;

    @PostMapping("/add")
    @Operation(summary = "Add Booking Agent")
    public ResponseEntity<Response<BookingAgentDTO>> add(@RequestBody @Valid BookingAgentCreateDTO dto) {
        BookingAgentDTO result = bookingAgentService.add(dto);
        return ResponseMaker.ok(result);
    }
}
