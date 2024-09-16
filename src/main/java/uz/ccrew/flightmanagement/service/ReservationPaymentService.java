package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.dto.reservationpayment.PaymentDTO;
import uz.ccrew.flightmanagement.entity.ItineraryReservation;

import java.util.List;
import java.util.UUID;

public interface ReservationPaymentService {
    List<PaymentDTO> getPaymentList(Long reservationId);

    void confirmReservation(UUID paymentId);

    void reverseReservation(UUID paymentId);

    void add(ItineraryReservation reservation, Long amount);
}
