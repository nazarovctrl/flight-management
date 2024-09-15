package uz.ccrew.flightmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import uz.ccrew.flightmanagement.entity.FlightSchedule;

@Repository
public interface FlightScheduleRepository extends BasicRepository<FlightSchedule, Long> {
    Page<FlightSchedule> findByOriginAirport_AirportCode(String airportCode, Pageable pageable);

    @Query("SELECT DISTINCT fs FROM FlightSchedule fs " +
            "JOIN Leg l ON fs.flightNumber = l.flightSchedule.flightNumber " +
            "WHERE fs.departureDateTime = l.actualDepartureTime " +
            "AND fs.arrivalDateTime = l.actualArrivalTime")
    Page<FlightSchedule> findOnTimeFlights(Pageable pageable);

    @Query("SELECT DISTINCT fs FROM FlightSchedule fs " +
            "JOIN Leg l ON fs.flightNumber = l.flightSchedule.flightNumber " +
            "WHERE fs.departureDateTime != l.actualDepartureTime " +
            "OR fs.arrivalDateTime != l.actualArrivalTime")
    Page<FlightSchedule> findDelayedFlights(Pageable pageable);
}
