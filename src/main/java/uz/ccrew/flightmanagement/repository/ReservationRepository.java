package uz.ccrew.flightmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ccrew.flightmanagement.entity.ItineraryReservation;

public interface ReservationRepository extends BasicRepository<ItineraryReservation, Long> {
    Page<ItineraryReservation> findByPassenger_CustomerId(Long customerId, Pageable pageable);
}
