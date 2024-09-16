package uz.ccrew.flightmanagement.dto.reservation;

import uz.ccrew.flightmanagement.entity.FlightSchedule;

public record RoundTrip(FlightSchedule flight,
                        FlightSchedule returnFlight) {
}
