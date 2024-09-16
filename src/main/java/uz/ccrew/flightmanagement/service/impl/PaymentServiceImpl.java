package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.entity.Payment;
import uz.ccrew.flightmanagement.mapper.PaymentMapper;
import uz.ccrew.flightmanagement.service.PaymentService;
import uz.ccrew.flightmanagement.enums.PaymentStatusCode;
import uz.ccrew.flightmanagement.exp.BadRequestException;
import uz.ccrew.flightmanagement.repository.PaymentRepository;
import uz.ccrew.flightmanagement.dto.reservationpayment.PaymentDTO;
import uz.ccrew.flightmanagement.service.ReservationPaymentService;

import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final ReservationPaymentService reservationPaymentService;

    @Transactional
    @Override
    public PaymentDTO pay(UUID paymentId) {
        Payment payment = paymentRepository.loadById(paymentId);
        if (!payment.getPaymentStatusCode().equals(PaymentStatusCode.CREATED)) {
            throw new BadRequestException("Invalid payment status");
        }
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatusCode(PaymentStatusCode.PAYED);
        paymentRepository.save(payment);

        reservationPaymentService.confirmReservation(paymentId);

        return paymentMapper.toDTO(payment);
    }

    @Transactional
    @Override
    public PaymentDTO reverse(UUID paymentId) {
        Payment payment = paymentRepository.loadById(paymentId);
        if (!payment.getPaymentStatusCode().equals(PaymentStatusCode.PAYED)) {
            throw new BadRequestException("Invalid payment status");
        }
        payment.setPaymentStatusCode(PaymentStatusCode.REVERSED);
        paymentRepository.save(payment);

        reservationPaymentService.reverseReservation(paymentId);

        return paymentMapper.toDTO(payment);
    }

}
