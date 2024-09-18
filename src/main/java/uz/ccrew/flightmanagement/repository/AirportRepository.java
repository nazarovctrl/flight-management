package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.Airport;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirportRepository extends BasicRepository<Airport, String> {
    Optional<Airport> findByAirportCode(String airportCode);

    @Query(""" 
            select distinct w.city
              from Airport w
            """)
    List<String> getCityList();

    Optional<Airport> findFirstByCity(String city);
}
