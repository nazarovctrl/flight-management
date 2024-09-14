package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.Leg;

import java.util.List;

public interface LegRepository extends BasicRepository<Leg, Long> {
    boolean existsByFlightSchedule_FlightNumberAndOriginAirportAndDestinationAirport(Long flightNumber, String originAirport, String destinationAirport);
    List<Leg> findAllByFlightSchedule_FlightNumber(Long flightNumber);
}
