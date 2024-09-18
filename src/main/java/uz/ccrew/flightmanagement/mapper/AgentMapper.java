package uz.ccrew.flightmanagement.mapper;

import uz.ccrew.flightmanagement.entity.BookingAgent;
import uz.ccrew.flightmanagement.dto.reservation.BookingAgentDTO;

import org.springframework.stereotype.Component;

@Component
public class AgentMapper implements Mapper<Object, BookingAgentDTO, BookingAgent> {
    @Override
    public BookingAgent toEntity(Object o) {
        return null;
    }

    @Override
    public BookingAgentDTO toDTO(BookingAgent entity) {
        if (entity == null) return null;

        return BookingAgentDTO.builder()
                .agentId(entity.getAgentId())
                .agentName(entity.getAgentName())
                .agentDetails(entity.getAgentDetails())
                .build();
    }
}
