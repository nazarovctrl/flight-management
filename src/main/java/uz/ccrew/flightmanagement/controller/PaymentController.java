package uz.ccrew.flightmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.service.PaymentService;
import uz.ccrew.flightmanagement.dto.reservationpayment.PaymentDTO;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@Tag(name = "Payment Controller", description = "Payment API")
@SecurityRequirement(name = "Bearer Authentication")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/pay/{paymentId}")
    @Operation(summary = "Pay payment")
    private ResponseEntity<Response<PaymentDTO>> pay(@PathVariable("paymentId") String paymentId) {
        PaymentDTO result = paymentService.pay(UUID.fromString(paymentId));
        return ResponseMaker.ok(result);
    }

    @PostMapping("/reverse/{paymentId}")
    @Operation(summary = "Reverse payment")
    private ResponseEntity<Response<PaymentDTO>> reverse(@PathVariable("paymentId") String paymentId) {
        PaymentDTO result = paymentService.reverse(UUID.fromString(paymentId));
        return ResponseMaker.ok(result);
    }
}
