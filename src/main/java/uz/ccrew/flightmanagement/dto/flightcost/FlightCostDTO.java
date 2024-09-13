package uz.ccrew.flightmanagement.dto.flightcost;

import uz.ccrew.flightmanagement.enums.AircraftTypeCode;

import lombok.Builder;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Builder
@Schema(description = "Request body for save Flight cost")
public record FlightCostDTO(@NotNull(message = "Flight number can not be null")
                            Long flightNumber,
                            @NotNull(message = "Aircraft type code can not be null")
                            AircraftTypeCode aircraftTypeCode,
                            @NotNull
                            LocalDate validFromDate,
                            @NotNull
                            LocalDate validToDate) {
}
