package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.FlightSchedule;
import uz.ccrew.flightmanagement.entity.ItineraryReservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends BasicRepository<ItineraryReservation, Long> {
    Page<ItineraryReservation> findByPassenger_CustomerId(Long customerId, Pageable pageable);

    @Query("""
            select distinct l.leg.flightSchedule
              from ItineraryLeg l
             where l.reservation.reservationId = ?1
            """)
    List<FlightSchedule> getFlightListByReservationId(Long reservationId);
}
