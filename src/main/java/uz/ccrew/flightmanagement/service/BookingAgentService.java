package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.dto.reservation.BookingAgentDTO;
import uz.ccrew.flightmanagement.dto.bookingagent.BookingAgentCreateDTO;

public interface BookingAgentService {
    BookingAgentDTO add(BookingAgentCreateDTO dto);
}
