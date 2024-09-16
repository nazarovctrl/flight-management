package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.ItineraryReservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationRepository extends BasicRepository<ItineraryReservation, Long> {
    Page<ItineraryReservation> findByPassenger_CustomerId(Long customerId, Pageable pageable);
}
