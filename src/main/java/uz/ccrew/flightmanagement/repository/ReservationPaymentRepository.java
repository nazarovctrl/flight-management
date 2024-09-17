package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.Payment;
import uz.ccrew.flightmanagement.entity.ReservationPayment;
import uz.ccrew.flightmanagement.entity.ItineraryReservation;

import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ReservationPaymentRepository extends BasicRepository<ReservationPayment, ReservationPayment.ReservationPaymentId> {

    @Query(""" 
            select w.payment
              from ReservationPayment w
             where w.reservation.reservationId = ?1
            """)
    List<Payment> findByReservationId(Long reservationId);

    @Query("""
            select w.reservation
              from ReservationPayment w
             where w.payment.paymentId = ?1
            """)
    List<ItineraryReservation> findByPaymentId(UUID paymentId);
}
