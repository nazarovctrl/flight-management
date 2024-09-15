package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.enums.AircraftTypeCode;
import uz.ccrew.flightmanagement.entity.TravelClassCapacity;

import java.util.List;

public interface TravelClassCapacityRepository extends BasicRepository<TravelClassCapacity, TravelClassCapacity.TravelClassCapacityId> {
    List<TravelClassCapacity> findById_AircraftTypeCode(AircraftTypeCode aircraftTypeCode);
}
