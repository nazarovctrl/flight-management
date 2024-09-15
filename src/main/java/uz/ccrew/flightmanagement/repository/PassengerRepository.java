package uz.ccrew.flightmanagement.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import uz.ccrew.flightmanagement.entity.Passenger;

@Repository
public interface PassengerRepository extends BasicRepository<Passenger, Long> {
    boolean existsByCustomerId(Long customerId);

    @Query("SELECT p FROM Passenger p WHERE p.passengerId IN " +
            "(SELECT ir.passenger.passengerId FROM ItineraryReservation ir " +
            "WHERE ir.reservationId IN " +
            "(SELECT il.reservation.reservationId FROM ItineraryLeg il " +
            "WHERE il.leg.flightSchedule.flightNumber = :flightNumber))")
    Page<Passenger> findPassengersWithReservedSeatsOnFlight(@Param("flightNumber") String flightNumber, Pageable pageable);
}
