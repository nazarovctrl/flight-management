package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.Passenger;

public interface PassengerRepository extends BasicRepository<Passenger, Long> {
    boolean existsByCustomerId(Long customerId);
}
