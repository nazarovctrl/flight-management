package uz.ccrew.flightmanagement.mapper;

import uz.ccrew.flightmanagement.entity.ItineraryReservation;
import uz.ccrew.flightmanagement.dto.reservation.ReservationDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationMapper implements Mapper<Object, ReservationDTO, ItineraryReservation> {
    private final AgentMapper agentMapper;
    private final PassengerMapper passengerMapper;

    @Override
    public ItineraryReservation toEntity(Object o) {
        return null;
    }

    @Override
    public ReservationDTO toDTO(ItineraryReservation entity) {
        return ReservationDTO.builder()
                .reservationId(entity.getReservationId())
                .agentDTO(agentMapper.toDTO(entity.getAgent()))
                .passengerDTO(passengerMapper.toDTO(entity.getPassenger()))
                .reservationStatusCode(entity.getReservationStatusCode())
                .ticketTypeCode(entity.getTicketTypeCode())
                .travelClassCode(entity.getTravelClassCode())
                .dateReservationMade(entity.getDateReservationMade())
                .numberInParty(entity.getNumberInParty())
                .build();
    }
}
