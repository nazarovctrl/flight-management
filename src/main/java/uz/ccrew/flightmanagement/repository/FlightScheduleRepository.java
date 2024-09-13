package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.FlightSchedule;

import org.springframework.stereotype.Repository;

@Repository
public interface FlightScheduleRepository extends BasicRepository<FlightSchedule, Long> {

}
