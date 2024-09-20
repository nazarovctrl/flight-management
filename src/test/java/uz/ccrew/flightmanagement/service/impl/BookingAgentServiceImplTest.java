package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.service.BookingAgentService;
import uz.ccrew.flightmanagement.dto.reservation.BookingAgentDTO;
import uz.ccrew.flightmanagement.dto.bookingagent.BookingAgentCreateDTO;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class BookingAgentServiceImplTest {

    @Autowired
    private BookingAgentService bookingAgentService;

    @Test
    void add() {
        BookingAgentCreateDTO bookingAgentCreateDTO = BookingAgentCreateDTO.builder()
                .agentName("Anton")
                .agentDetails("smth")
                .build();

        BookingAgentDTO bookingAgentDTO = bookingAgentService.add(bookingAgentCreateDTO);

        assertThat(bookingAgentDTO).isNotNull();
        assertThat(bookingAgentDTO.agentId()).isEqualTo(1);
    }

    @Test
    void getList() {
        List<BookingAgentDTO> bookingAgentDTOList = bookingAgentService.getList();

        assertThat(bookingAgentDTOList).isNotNull();
        assertThat(bookingAgentDTOList.isEmpty()).isEqualTo(true);
    }
}
