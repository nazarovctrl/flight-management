package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.FlightSchedule;
import uz.ccrew.flightmanagement.entity.ItineraryReservation;
import uz.ccrew.flightmanagement.entity.Passenger;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationRepository extends BasicRepository<ItineraryReservation, Long> {
    Page<ItineraryReservation> findByPassenger_CustomerId(Long customerId, Pageable pageable);

    @Query(value = """
            select l.reservation.passenger
            from ItineraryLeg l
            where l.leg.flightSchedule.flightNumber = ?1
            and l.reservation.reservationStatusCode = 'CONFIRMED'
            order by l.reservation.dateReservationMade
            """)
    Page<Passenger> findPassengersWithReservedSeatsOnFlight(String flightNumber, Pageable pageable);

    @Query("""
            select distinct l.leg.flightSchedule
              from ItineraryLeg l
             where l.reservation.reservationId = ?1
            """)
    List<FlightSchedule> getFlightListByReservationId(Long reservationId);
}
