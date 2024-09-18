package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.dto.reservation.BookingAgentDTO;
import uz.ccrew.flightmanagement.dto.bookingagent.BookingAgentCreateDTO;

import java.util.List;

public interface BookingAgentService {
    BookingAgentDTO add(BookingAgentCreateDTO dto);

    List<BookingAgentDTO> getList();
}
