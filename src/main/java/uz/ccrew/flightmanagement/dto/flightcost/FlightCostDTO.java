package uz.ccrew.flightmanagement.dto.flightcost;

import uz.ccrew.flightmanagement.enums.AircraftTypeCode;

import lombok.Builder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Builder
@Schema(description = "Request body for save Flight cost")
public record FlightCostDTO(@NotNull(message = "Flight number can not be null")
                            Long flightNumber,
                            @NotNull(message = "Aircraft type code can not be null")
                            AircraftTypeCode aircraftTypeCode,
                            @NotNull(message = "Valid from date can not be null")
                            LocalDate validFromDate,
                            @NotNull(message = "Valid to date can not be null")
                            LocalDate validToDate,
                            @NotNull(message = "FLight cost can not be null")
                            @Min(value = 0, message = "Invalid flight cost")
                            Long flightCost) {
}
