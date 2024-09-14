package uz.ccrew.flightmanagement.repository;

import org.springframework.data.jpa.repository.Query;
import uz.ccrew.flightmanagement.entity.Leg;

import java.util.List;

public interface LegRepository extends BasicRepository<Leg, Long> {
    boolean existsByFlightSchedule_FlightNumberAndOriginAirportAndDestinationAirport(Long flightNumber, String originAirport, String destinationAirport);
    @Query("SELECT l FROM Leg l WHERE l.flightSchedule.flightNumber = :flightNumber")
    List<Leg> findAllByFlightNumber(Long flightNumber);
}
