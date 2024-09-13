package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.FlightCost;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightCostRepository extends JpaRepository<FlightCost, FlightCost.FlightCostsId> {
}
