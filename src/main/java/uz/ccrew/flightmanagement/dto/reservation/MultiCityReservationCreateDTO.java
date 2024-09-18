package uz.ccrew.flightmanagement.dto.reservation;

import lombok.Builder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Builder
@Schema(description = "Request body for make one way reservation")
public record MultiCityReservationCreateDTO(@NotNull(message = "Invalid flight number")
                                            @Size(min = 3, message = "The flightNumbers list must contain at least 3 flights")
                                            List<Long> flightNumbers,
                                            @Valid
                                            @NotNull(message = "Main info can not be null")
                                            MainDTO main) {
}
