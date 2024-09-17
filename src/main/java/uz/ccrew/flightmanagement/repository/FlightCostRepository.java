package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.FlightCost;

import java.util.List;
import java.time.LocalDate;

public interface FlightCostRepository extends BasicRepository<FlightCost, FlightCost.FlightCostsId> {
    List<FlightCost> findByFlightSchedule_FlightNumberAndId_ValidFromDateLessThanEqualAndValidToDateGreaterThanEqual(Long flightSchedule_flightNumber, LocalDate validToDate, LocalDate validToDate2);
}

