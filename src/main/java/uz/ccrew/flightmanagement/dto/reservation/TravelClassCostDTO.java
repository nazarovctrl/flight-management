package uz.ccrew.flightmanagement.dto.reservation;

import uz.ccrew.flightmanagement.enums.TravelClassCode;

public record TravelClassCostDTO(TravelClassCode travelClassCode, Long flightCost) {
}
