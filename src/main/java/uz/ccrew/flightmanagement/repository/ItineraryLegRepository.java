package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.ItineraryLeg;
import uz.ccrew.flightmanagement.entity.FlightSchedule;
import uz.ccrew.flightmanagement.dto.reservation.TravelClassSeatDTO;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItineraryLegRepository extends BasicRepository<ItineraryLeg, Long> {
    @Query("""
            select new uz.ccrew.flightmanagement.dto.reservation.TravelClassSeatDTO(w.reservation.travelClassCode, (count(*) / ?2) )
              from ItineraryLeg w
             where w.leg.flightSchedule.flightNumber = ?1
             and w.reservation.reservationStatusCode = 'CONFIRMED'
             group by w.reservation.travelClassCode
            """)
    List<TravelClassSeatDTO> getTravelClassReservedSeatsByFlight(Long flightNumber, int legCount);

    @Query("""
            select w.leg.flightSchedule from ItineraryLeg w
            where w.reservation.reservationId = ?1
            order by w.leg.flightSchedule.departureDateTime asc
            """)
    List<FlightSchedule> findFlightByReservationId(Long reservationId);
}
