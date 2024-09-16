package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.dto.travelclasscapacity.TravelClassCapacityDTO;
import uz.ccrew.flightmanagement.dto.travelclasscapacity.TravelClassCapacityCreateDTO;

public interface TravelSeatCapacityService {
    TravelClassCapacityDTO add(TravelClassCapacityCreateDTO dto);
}
