package uz.ccrew.flightmanagement.dto.flightSchedule;

import java.time.LocalDateTime;

public class FlightScheduleReportDTO {
    private Long flightNumber;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private LocalDateTime actualDepartureDateTime;
    private LocalDateTime actualArrivalDateTime;
}
