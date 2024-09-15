package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.Passenger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PassengerRepository extends BasicRepository<Passenger, Long> {
    boolean existsByCustomerId(Long customerId);

    @Query("SELECT DISTINCT p FROM Passenger p " +
            "JOIN ItineraryReservation ir ON p.passengerId = ir.passenger.passengerId " +
            "JOIN ItineraryLeg il ON ir.reservationId = il.reservation.reservationId " +
            "JOIN Leg l ON il.leg.legId = l.legId " +
            "JOIN FlightSchedule fs ON l.flightSchedule.flightNumber = fs.flightNumber " +
            "WHERE fs.flightNumber = :flightNumber " +
            "AND ir.reservationStatusCode = 'CONFIRMED' " +
            "AND l.actualDepartureTime > CURRENT_TIMESTAMP")
    Page<Passenger> findPassengersWithReservedSeatsOnFlight(@Param("flightNumber") String flightNumber, Pageable pageable);
}
