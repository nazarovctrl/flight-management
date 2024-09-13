package uz.ccrew.flightmanagement.dto.refcalendar;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;

@Builder
@Schema(description = "Request body for save Ref Calendar")
public record RefCalendarDTO(@NotNull(message = "Day date can not be null")
                             LocalDate dayDate,
                             @Min(value = 0, message = "Invalid day number ")
                             Integer dayNumber,
                             @NotNull(message = "Business day yes/no can not be null")
                             Boolean businessDayYn) {
}
