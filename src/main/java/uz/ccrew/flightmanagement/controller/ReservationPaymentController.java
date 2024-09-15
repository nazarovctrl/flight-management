package uz.ccrew.flightmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.dto.reservationpayment.PaymentDTO;
import uz.ccrew.flightmanagement.service.ReservationPaymentService;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;


@RestController
@RequestMapping("/api/v1/reservation-payment")
@RequiredArgsConstructor
@Tag(name = "Reservation Payment Controller", description = "Reservation Payment API")
@SecurityRequirement(name = "Bearer Authentication")
public class ReservationPaymentController {
    private final ReservationPaymentService reservationPaymentService;

    @GetMapping("/get/payment-list/{reservationId}")
    @Operation(summary = "Get payment list by reservationId")
    public ResponseEntity<Response<List<PaymentDTO>>> getPaymentList(@PathVariable("reservationId") Long reservationId) {
        List<PaymentDTO> result = reservationPaymentService.getPaymentList(reservationId);
        return ResponseMaker.ok(result);
    }
}
