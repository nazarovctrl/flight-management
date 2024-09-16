package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.FlightCost;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDate;

@Repository
public interface FlightCostRepository extends BasicRepository<FlightCost, FlightCost.FlightCostsId> {
    List<FlightCost> findByFlightSchedule_FlightNumberAndId_ValidFromDateLessThanEqualAndValidToDateGreaterThanEqual(Long flightSchedule_flightNumber, LocalDate validToDate, LocalDate validToDate2);
    @Query("SELECT SUM(fc.flightCost) FROM FlightCost fc WHERE fc.flightSchedule.flightNumber = :flightNumber")
    Long calculateTotalSalesByFlightNumber(@Param("flightNumber") Long flightNumber);
}

