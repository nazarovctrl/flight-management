package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.enums.AircraftTypeCode;
import uz.ccrew.flightmanagement.entity.TravelClassCapacity;
import uz.ccrew.flightmanagement.enums.TravelClassCode;

import java.util.List;

public interface TravelClassCapacityRepository extends BasicRepository<TravelClassCapacity, TravelClassCapacity.TravelClassCapacityId> {
    List<TravelClassCapacity> findById_AircraftTypeCode(AircraftTypeCode aircraftTypeCode);
    boolean existsById_AircraftTypeCodeAndId_TravelClassCode(AircraftTypeCode aircraftTypeCode, TravelClassCode classCode);
}
