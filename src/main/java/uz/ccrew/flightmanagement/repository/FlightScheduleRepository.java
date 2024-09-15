package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.FlightSchedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface FlightScheduleRepository extends BasicRepository<FlightSchedule, Long> {

    @Query(value = """ 
            select * from flight_schedules as w 
             where w.origin_airport_code = ?1
            order by  abs(extract(epoch from (departure_date_time - now()))) asc """,
            nativeQuery = true)
    Page<FlightSchedule> findByAirportCode(String airportCode, Pageable pageable);

    @Query(value = """
            select w from FlightSchedule w
             where date(w.departureDateTime) = ?3
               and w.originAirport.city = ?1
               and w.destinationAirport.city = ?2
               and exists (select 1 from Leg l
                            where l.flightSchedule = w
                              and l.destinationAirport = w.destinationAirport.airportCode)
               and exists (select 1 from FlightCost c
                            where c.flightSchedule.flightNumber= w.flightNumber
                              and CURRENT_TIMESTAMP between c.id.validFromDate and c.validToDate)
             order by w.departureDateTime asc
             """)
    List<FlightSchedule> findOneWay(String departureCity, String arrivalCity, LocalDate departureDate);
}
