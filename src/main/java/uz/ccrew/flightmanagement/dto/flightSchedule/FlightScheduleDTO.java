package uz.ccrew.flightmanagement.dto.flightSchedule;

import uz.ccrew.flightmanagement.dto.leg.LegDTO;
import uz.ccrew.flightmanagement.enums.AirlineCode;
import uz.ccrew.flightmanagement.enums.AircraftTypeCode;

import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record FlightScheduleDTO(Long flightNumber,
                                AirlineCode airlineCode,
                                AircraftTypeCode usualAircraftTypeCode,
                                String originAirportCode,
                                String destinationAirportCode,
                                LocalDateTime departureDateTime,
                                LocalDateTime arrivalDateTime,
                                List<LegDTO> legDTOList){}
