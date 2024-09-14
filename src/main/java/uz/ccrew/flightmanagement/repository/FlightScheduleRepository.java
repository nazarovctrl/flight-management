package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.FlightSchedule;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightScheduleRepository extends BasicRepository<FlightSchedule, Long> {
    List<FlightSchedule> findByOriginAirport_AirportCode(String originAirportCode);
}
