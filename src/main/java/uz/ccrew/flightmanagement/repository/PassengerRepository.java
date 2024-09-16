package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.Passenger;

import java.util.Optional;

public interface PassengerRepository extends BasicRepository<Passenger, Long> {
    boolean existsByCustomerId(Long customerId);

    Optional<Passenger> findByCustomer_Id(Long customerId);
}
