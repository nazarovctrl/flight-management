package uz.ccrew.flightmanagement.dto.travelclasscapacity;

import uz.ccrew.flightmanagement.enums.TravelClassCode;
import uz.ccrew.flightmanagement.enums.AircraftTypeCode;

import lombok.Builder;

@Builder
public record TravelClassCapacityDTO(AircraftTypeCode aircraftTypeCode,
                                     TravelClassCode travelClassCode,
                                     Integer seatCapacity) {
}
