package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.FlightSchedule;
import uz.ccrew.flightmanagement.enums.AircraftTypeCode;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface FlightScheduleRepository extends BasicRepository<FlightSchedule, Long> {
    boolean existsByUsualAircraftTypeCodeAndDepartureDateTimeBeforeAndArrivalDateTimeAfter(
            AircraftTypeCode aircraftTypeCode, LocalDateTime arrivalDateTime, LocalDateTime departureDateTime);
}
