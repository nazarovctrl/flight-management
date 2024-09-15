package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.dto.reservation.OneWayReservationCreateDTO;
import uz.ccrew.flightmanagement.dto.reservation.ReservationDTO;

public interface ReservationService {
    ReservationDTO makeOneWay(OneWayReservationCreateDTO dto);
}
