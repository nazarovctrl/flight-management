package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.FlightSchedule;
import uz.ccrew.flightmanagement.dto.flightSchedule.RoundTrip;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleReportDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface FlightScheduleRepository extends BasicRepository<FlightSchedule, Long> {
    @Query("""
            select distinct new uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleReportDTO(fs.flightNumber,fs.departureDateTime,fs.arrivalDateTime,l.actualDepartureTime,l.actualArrivalTime)
              from FlightSchedule fs
              join Leg l on fs.flightNumber = l.flightSchedule.flightNumber
             where fs.departureDateTime = l.actualDepartureTime
               and fs.arrivalDateTime = l.actualArrivalTime
               and l.originAirport = fs.originAirport.airportCode
            """)
    Page<FlightScheduleReportDTO> findOnTimeFlights(Pageable pageable);

    @Query("""
            select distinct new uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleReportDTO(fs.flightNumber, fs.departureDateTime,fs.arrivalDateTime,l.actualDepartureTime,l.actualArrivalTime)
              from FlightSchedule fs
              join Leg l on fs.flightNumber = l.flightSchedule.flightNumber
             where l.originAirport = fs.originAirport.airportCode
               and ((l.actualDepartureTime is not null and fs.departureDateTime < l.actualDepartureTime)
                     or (l.actualArrivalTime is not null and fs.arrivalDateTime < l.actualArrivalTime))
            """)
    Page<FlightScheduleReportDTO> findDelayedFlights(Pageable pageable);

    @Query(value = """ 
            select * 
              from flight_schedules as w 
             where w.origin_airport_code = ?1
             order by  abs(extract(epoch from (departure_date_time - now()))) asc
             """,
            nativeQuery = true)
    Page<FlightSchedule> findByAirportCode(String airportCode, Pageable pageable);

    @Query("""
            select w
              from FlightSchedule w
             where date(w.departureDateTime) = ?3
               and w.originAirport.city = ?1
               and w.destinationAirport.city = ?2
               and exists (select 1 from Leg l
                            where l.flightSchedule = w
                              and l.destinationAirport = w.destinationAirport.airportCode)
               and exists (select 1 from FlightCost c
                            where c.flightSchedule.flightNumber= w.flightNumber
                              and CURRENT_TIMESTAMP between c.id.validFromDate and c.validToDate)
             """)
    List<FlightSchedule> findOneWay(String departureCity, String arrivalCity, LocalDate departureDate);

    @Query("""
            select new uz.ccrew.flightmanagement.dto.flightSchedule.RoundTrip(w, f)
              from FlightSchedule w
              join FlightSchedule f
                on f.airlineCode = w.airlineCode
             where w.flightNumber != f.flightNumber
               and w.departureDateTime < f.departureDateTime
               and date(w.departureDateTime) = ?3
               and date(f.departureDateTime) = ?4
               and w.originAirport.city = ?1
               and w.destinationAirport.city = ?2
               and f.originAirport.city = w.destinationAirport.city
               and f.destinationAirport.city = w.originAirport.city
               and exists (select 1 from Leg l
                            where l.flightSchedule = w
                              and l.destinationAirport = w.destinationAirport.airportCode)
               and exists (select 1 from FlightCost c
                            where c.flightSchedule.flightNumber= w.flightNumber
                              and CURRENT_TIMESTAMP between c.id.validFromDate and c.validToDate)
               and exists (select 1 from Leg l
                            where l.flightSchedule = f
                              and l.destinationAirport = f.destinationAirport.airportCode)
               and exists (select 1 from FlightCost c
                            where c.flightSchedule.flightNumber= f.flightNumber
                              and CURRENT_TIMESTAMP between c.id.validFromDate and c.validToDate)
             """)
    List<RoundTrip> findRoundTrip(String departureCity, String s1, LocalDate localDate, LocalDate localDate1);

    @Query("""
            select w
              from FlightSchedule w
             where w.originAirport.city = ?1
               and w.departureDateTime between ?2 and ?3
               and exists (select 1
                             from Leg l
                            where l.flightSchedule = w
                              and l.destinationAirport = w.destinationAirport.airportCode)
               and exists (select 1
                             from FlightCost c
                            where c.flightSchedule.flightNumber= w.flightNumber
                              and CURRENT_TIMESTAMP between c.id.validFromDate and c.validToDate)
            """)
    List<FlightSchedule> findByOriginAirportCityAndTimeConstraints(String city, LocalDateTime minDepartureDate, LocalDateTime maxDepartureDate);

    @Query("""
            select w
              from FlightSchedule w
             where w.originAirport.city = ?1
               and exists (select 1
                             from Leg l
                            where l.flightSchedule = w
                              and l.destinationAirport = w.destinationAirport.airportCode)
               and exists (select 1
                             from FlightCost c
                            where c.flightSchedule.flightNumber= w.flightNumber
                              and CURRENT_TIMESTAMP between c.id.validFromDate and c.validToDate)
            """)
    List<FlightSchedule> findByOriginAirportCity(String city);
}
