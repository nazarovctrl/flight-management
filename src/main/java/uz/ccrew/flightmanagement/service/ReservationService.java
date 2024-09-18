package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.dto.reservation.*;
import uz.ccrew.flightmanagement.enums.TravelClassCode;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleDTO;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ReservationService {
    ReservationDTO makeOneWay(OneWayReservationCreateDTO dto);

    void checkToConfirmation(Long reservationId, TravelClassCode travelClassCode);

    void reverseReservation(Long reservationId);

    ReservationDTO cancel(Long reservationId);

    Page<ReservationDTO> getList(int page, int size);

    ReservationDTO makeRoundTrip(RoundTripReservationCreate dto);

    List<FlightScheduleDTO> getFlightList(Long reservationId);

    ReservationDTO makeFlexible(ReservationFlexibleDTO dto);

    ReservationDTO makeMultiCity(MultiCityReservationCreateDTO dto);
}
