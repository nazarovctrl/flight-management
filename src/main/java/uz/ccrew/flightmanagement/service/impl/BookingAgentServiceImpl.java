package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.entity.BookingAgent;
import uz.ccrew.flightmanagement.exp.AlreadyExistException;
import uz.ccrew.flightmanagement.mapper.BookingAgentMapper;
import uz.ccrew.flightmanagement.service.BookingAgentService;
import uz.ccrew.flightmanagement.dto.reservation.BookingAgentDTO;
import uz.ccrew.flightmanagement.repository.BookingAgentRepository;
import uz.ccrew.flightmanagement.dto.bookingagent.BookingAgentCreateDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingAgentServiceImpl implements BookingAgentService {
    private final BookingAgentMapper bookingAgentMapper;
    private final BookingAgentRepository bookingAgentRepository;

    @Override
    public BookingAgentDTO add(BookingAgentCreateDTO dto) {
        if (bookingAgentRepository.existsByAgentName(dto.agentName())) {
            throw new AlreadyExistException("Booking agent with this name already exists");
        }
        BookingAgent entity = bookingAgentMapper.toEntity(dto);
        bookingAgentRepository.save(entity);

        return bookingAgentMapper.toDTO(entity);
    }
}
