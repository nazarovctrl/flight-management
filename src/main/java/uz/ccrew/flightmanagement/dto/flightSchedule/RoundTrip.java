package uz.ccrew.flightmanagement.dto.flightSchedule;

import uz.ccrew.flightmanagement.entity.FlightSchedule;

public record RoundTrip(FlightSchedule flight,
                        FlightSchedule returnFlight) {
}
