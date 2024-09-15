package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.dto.reservation.TravelClassSeatDTO;
import uz.ccrew.flightmanagement.entity.ItineraryLeg;
import uz.ccrew.flightmanagement.enums.TravelClassCode;

import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;
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
}
