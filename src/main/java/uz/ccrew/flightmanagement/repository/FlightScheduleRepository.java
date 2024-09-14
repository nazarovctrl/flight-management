package uz.ccrew.flightmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import uz.ccrew.flightmanagement.entity.FlightSchedule;

@Repository
public interface FlightScheduleRepository extends BasicRepository<FlightSchedule, Long> {
    Page<FlightSchedule> findByOriginAirport_AirportCode(String airportCode, Pageable pageable);
}
