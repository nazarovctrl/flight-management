package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.enums.TravelClassCode;
import uz.ccrew.flightmanagement.dto.reservation.ReservationDTO;
import uz.ccrew.flightmanagement.dto.reservation.OneWayReservationCreateDTO;

public interface ReservationService {
    ReservationDTO makeOneWay(OneWayReservationCreateDTO dto);

    void checkToAvailabilityWithReservationId(Long reservationId, TravelClassCode travelClassCode);

    void reverseReservation(Long reservationId);
}
