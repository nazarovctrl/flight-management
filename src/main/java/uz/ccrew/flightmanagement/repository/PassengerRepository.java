package uz.ccrew.flightmanagement.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import uz.ccrew.flightmanagement.entity.Passenger;

@Repository
public interface PassengerRepository extends BasicRepository<Passenger, Long> {
    boolean existsByCustomerId(Long customerId);

    @Query("SELECT DISTINCT p FROM Passenger p " +
            "JOIN p.customer u " +
            "JOIN ItineraryReservation ir ON ir.passenger = p " +
            "JOIN ItineraryLeg il ON il.reservation = ir " +
            "JOIN Leg l ON l = il.leg " +
            "WHERE l.flightSchedule.flightNumber = :flightNumber")
    Page<Passenger> findPassengersWithReservedSeatsOnFlight(String flightNumber, Pageable pageable);
}