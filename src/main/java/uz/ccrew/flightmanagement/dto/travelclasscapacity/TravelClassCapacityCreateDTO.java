package uz.ccrew.flightmanagement.dto.travelclasscapacity;

import uz.ccrew.flightmanagement.enums.TravelClassCode;
import uz.ccrew.flightmanagement.enums.AircraftTypeCode;

import lombok.Builder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Builder
@Schema(description = "Request body for create Travel Class Capacity")
public record TravelClassCapacityCreateDTO(@NotNull(message = "Air craft type code can not be null")
                                           AircraftTypeCode aircraftTypeCode,
                                           @NotNull(message = "Travel class code capacity can not be null")
                                           TravelClassCode travelClassCode,
                                           @NotNull(message = "Seat capacity can not be null")
                                           @Min(value = 0)
                                           Integer seatCapacity) {
}
