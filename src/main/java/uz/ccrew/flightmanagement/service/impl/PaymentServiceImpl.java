package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.entity.User;
import uz.ccrew.flightmanagement.util.AuthUtil;
import uz.ccrew.flightmanagement.entity.Payment;
import uz.ccrew.flightmanagement.mapper.PaymentMapper;
import uz.ccrew.flightmanagement.service.PaymentService;
import uz.ccrew.flightmanagement.enums.PaymentStatusCode;
import uz.ccrew.flightmanagement.exp.BadRequestException;
import uz.ccrew.flightmanagement.repository.UserRepository;
import uz.ccrew.flightmanagement.repository.PaymentRepository;
import uz.ccrew.flightmanagement.dto.reservationpayment.PaymentDTO;
import uz.ccrew.flightmanagement.service.ReservationPaymentService;
import uz.ccrew.flightmanagement.repository.ReservationPaymentRepository;

import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final AuthUtil authUtil;
    private final PaymentMapper paymentMapper;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final ReservationPaymentService reservationPaymentService;
    private final ReservationPaymentRepository reservationPaymentRepository;

    @Transactional
    @Override
    public PaymentDTO pay(UUID paymentId) {
        checkToOwner(paymentId);

        Payment payment = paymentRepository.loadById(paymentId);
        if (!payment.getPaymentStatusCode().equals(PaymentStatusCode.CREATED)) {
            throw new BadRequestException("Invalid payment status");
        }
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatusCode(PaymentStatusCode.PAYED);
        paymentRepository.save(payment);

        User user = authUtil.loadLoggedUser();
        user.setCashbackAmount(payment.getPaymentAmount() / 100 + user.getCashbackAmount());
        userRepository.save(user);

        reservationPaymentService.confirmReservation(paymentId);

        return paymentMapper.toDTO(payment);
    }

    @Transactional
    @Override
    public PaymentDTO reverse(UUID paymentId) {
        checkToOwner(paymentId);

        Payment payment = paymentRepository.loadById(paymentId);
        if (!payment.getPaymentStatusCode().equals(PaymentStatusCode.PAYED)) {
            throw new BadRequestException("Invalid payment status");
        }
        payment.setPaymentStatusCode(PaymentStatusCode.REVERSED);
        paymentRepository.save(payment);

        reservationPaymentService.reverseReservation(paymentId);

        User user = authUtil.loadLoggedUser();
        // cashbackAmount minus ham boladi,
        // cashbacni ishlatgandan song reverse qilsa cashbackAmount<0 bo'ladi
        // yani keyingi sabar reservation qilgandan paymentAmount koproq bo'ladi
        user.setCashbackAmount(user.getCashbackAmount() - payment.getPaymentAmount() / 100);
        userRepository.save(user);

        return paymentMapper.toDTO(payment);
    }

    private void checkToOwner(UUID paymentId) {
        Long ownerId = reservationPaymentRepository.findReservationOwnerByPaymentId(paymentId);
        if (!authUtil.loadLoggedUser().getId().equals(ownerId)) {
            throw new BadRequestException("You cant pay/reverse this payment");
        }
    }
}
