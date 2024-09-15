package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.enums.TravelClassCode;
import uz.ccrew.flightmanagement.dto.reservation.ReservationDTO;
import uz.ccrew.flightmanagement.dto.reservation.OneWayReservationCreateDTO;

import org.springframework.data.domain.Page;

public interface ReservationService {
    ReservationDTO makeOneWay(OneWayReservationCreateDTO dto);

    void checkToAvailabilityWithReservationId(Long reservationId, TravelClassCode travelClassCode);

    void reverseReservation(Long reservationId);

    ReservationDTO cancel(Long reservationId);

    Page<ReservationDTO> getList(int page, int size);
}
