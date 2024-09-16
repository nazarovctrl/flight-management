package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.entity.Payment;
import uz.ccrew.flightmanagement.entity.ReservationPayment;
import uz.ccrew.flightmanagement.enums.PaymentStatusCode;
import uz.ccrew.flightmanagement.mapper.PaymentMapper;
import uz.ccrew.flightmanagement.repository.PaymentRepository;
import uz.ccrew.flightmanagement.service.ReservationService;
import uz.ccrew.flightmanagement.enums.ReservationStatusCode;
import uz.ccrew.flightmanagement.entity.ItineraryReservation;
import uz.ccrew.flightmanagement.repository.ReservationRepository;
import uz.ccrew.flightmanagement.service.ReservationPaymentService;
import uz.ccrew.flightmanagement.dto.reservationpayment.PaymentDTO;
import uz.ccrew.flightmanagement.repository.ReservationPaymentRepository;

import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationPaymentServiceImpl implements ReservationPaymentService {
    private final ReservationPaymentRepository reservationPaymentRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;
    private final ReservationService reservationService;
    private final PaymentMapper paymentMapper;

    @Override
    public List<PaymentDTO> getPaymentList(Long reservationId) {
        List<Payment> paymentList = reservationPaymentRepository.findByReservationId(reservationId);
        return paymentMapper.toDTOList(paymentList);
    }

    @Override
    public void confirmReservation(UUID paymentId) {
        List<ItineraryReservation> reservationList = reservationPaymentRepository.findByPaymentId(paymentId);
        for (ItineraryReservation reservation : reservationList) {
            reservationService.checkToAvailabilityWithReservationId(reservation.getReservationId(), reservation.getTravelClassCode());

            reservation.setReservationStatusCode(ReservationStatusCode.CONFIRMED);
            reservationRepository.save(reservation);
        }
    }

    @Transactional
    @Override
    public void reverseReservation(UUID paymentId) {
        List<ItineraryReservation> reservationList = reservationPaymentRepository.findByPaymentId(paymentId);

        for (ItineraryReservation reservation : reservationList) {
            reservationService.reverseReservation(reservation.getReservationId());

            reservation.setReservationStatusCode(ReservationStatusCode.CANCELED);
            reservationRepository.save(reservation);
        }
    }

    @Override
    public void add(ItineraryReservation reservation, Long amount) {
        Payment payment = Payment.builder()
                .paymentAmount(amount)
                .paymentStatusCode(PaymentStatusCode.CREATED)
                .build();
        paymentRepository.save(payment);

        ReservationPayment reservationPayment = ReservationPayment.builder()
                .id(new ReservationPayment.ReservationPaymentId(reservation.getReservationId(), payment.getPaymentId()))
                .payment(payment)
                .reservation(reservation)
                .build();
        reservationPaymentRepository.save(reservationPayment);
    }
}
