package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.dto.reservation.ReservationDTO;
import uz.ccrew.flightmanagement.dto.reservation.OneWayReservationCreateDTO;

public interface ReservationService {
    ReservationDTO makeOneWay(OneWayReservationCreateDTO dto);
}
