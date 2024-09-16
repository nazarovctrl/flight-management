package uz.ccrew.flightmanagement.mapper;

import uz.ccrew.flightmanagement.entity.BookingAgent;
import uz.ccrew.flightmanagement.dto.reservation.BookingAgentDTO;
import uz.ccrew.flightmanagement.dto.bookingagent.BookingAgentCreateDTO;

import org.springframework.stereotype.Component;

@Component
public class BookingAgentMapper implements Mapper<BookingAgentCreateDTO, BookingAgentDTO, BookingAgent> {
    @Override
    public BookingAgent toEntity(BookingAgentCreateDTO dto) {
        return BookingAgent.builder()
                .agentName(dto.agentName())
                .agentDetails(dto.agentDetails())
                .build();
    }

    @Override
    public BookingAgentDTO toDTO(BookingAgent entity) {
        return BookingAgentDTO.builder()
                .agentId(entity.getAgentId())
                .agentName(entity.getAgentName())
                .agentDetails(entity.getAgentDetails())
                .build();
    }
}
