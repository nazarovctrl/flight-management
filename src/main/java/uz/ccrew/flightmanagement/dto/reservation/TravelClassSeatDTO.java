package uz.ccrew.flightmanagement.dto.reservation;

import uz.ccrew.flightmanagement.enums.TravelClassCode;

import lombok.Getter;

@Getter
public class TravelClassSeatDTO {
    private TravelClassCode travelClassCode;
    private Integer reservedSeats;

    public TravelClassSeatDTO(TravelClassCode code, double reservedSeats) {
        this.travelClassCode = code;
        this.reservedSeats = (int) reservedSeats;
    }
}
