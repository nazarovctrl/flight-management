package uz.ccrew.flightmanagement.repository;

import org.springframework.stereotype.Repository;

import uz.ccrew.flightmanagement.entity.FlightSchedule;

import java.util.List;

@Repository
public interface FlightScheduleRepository extends BasicRepository<FlightSchedule, Long> {
    List<FlightSchedule> findByOriginAirport_AirportCode(String airportCode);
}
