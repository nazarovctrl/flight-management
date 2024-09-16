package uz.ccrew.flightmanagement.dto.reservation;

import lombok.Builder;

@Builder
public record BookingAgentDTO(Integer agentId,
                              String agentName,
                              String agentDetails) {
}
