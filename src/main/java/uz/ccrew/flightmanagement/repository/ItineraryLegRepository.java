package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.ItineraryLeg;
import uz.ccrew.flightmanagement.enums.TravelClassCode;

import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;

public interface ItineraryLegRepository extends BasicRepository<ItineraryLeg, Long> {
    @Query("""
            select w.reservation.travelClassCode, count(*) / ?2
              from ItineraryLeg w
             where w.leg.flightSchedule.flightNumber = ?1
             group by w.reservation.travelClassCode
            """)
    HashMap<TravelClassCode,Integer> getTravelClassReservedSeatsByFlight(Long flightNumber, int legCount);
}
