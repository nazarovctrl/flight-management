package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.dto.reservationpayment.PaymentDTO;

import java.util.UUID;

public interface PaymentService {
    PaymentDTO pay(UUID paymentId);

    PaymentDTO reverse(UUID paymentId);
}
