package uz.ccrew.flightmanagement.repository;

import org.springframework.stereotype.Repository;

import uz.ccrew.flightmanagement.entity.Passenger;

@Repository
public interface PassengerRepository extends BasicRepository<Passenger, Long> {
    boolean existsByCustomerId(Long customerId);

}
