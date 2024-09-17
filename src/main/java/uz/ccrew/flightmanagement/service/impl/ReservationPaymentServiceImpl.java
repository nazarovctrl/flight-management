package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.entity.Payment;
import uz.ccrew.flightmanagement.mapper.PaymentMapper;
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

        reservationList.parallelStream().forEach(reservation -> {
            reservationService.checkToConfirmation(reservation.getReservationId(), reservation.getTravelClassCode());
            reservation.setReservationStatusCode(ReservationStatusCode.CONFIRMED);
        });

        reservationRepository.saveAll(reservationList);
    }

    @Override
    public void reverseReservation(UUID paymentId) {
        List<ItineraryReservation> reservationList = reservationPaymentRepository.findByPaymentId(paymentId);

        reservationList.parallelStream().forEach(reservation -> {
            reservationService.reverseReservation(reservation.getReservationId());
            reservation.setReservationStatusCode(ReservationStatusCode.CANCELED);
        });

        reservationRepository.saveAll(reservationList);
    }
}
