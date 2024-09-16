package uz.ccrew.flightmanagement.dto.bookingagent;

import lombok.Builder;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema
@Builder
public record BookingAgentCreateDTO(@NotBlank(message = "Invalid agent name")
                                    String agentName,
                                    String agentDetails) {
}
