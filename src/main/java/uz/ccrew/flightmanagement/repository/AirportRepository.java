package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.Airport;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportRepository extends BasicRepository<Airport,String>{
    Optional<Airport> findByAirportCode(String airportCode);
}
