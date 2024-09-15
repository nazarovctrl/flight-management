package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.entity.User;
import uz.ccrew.flightmanagement.util.AuthUtil;
import uz.ccrew.flightmanagement.entity.Passenger;
import uz.ccrew.flightmanagement.mapper.PassengerMapper;
import uz.ccrew.flightmanagement.service.PassengerService;
import uz.ccrew.flightmanagement.exp.AlreadyExistException;
import uz.ccrew.flightmanagement.dto.passenger.PassengerDTO;
import uz.ccrew.flightmanagement.repository.PassengerRepository;
import uz.ccrew.flightmanagement.dto.passenger.PassengerCreateDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;
    private final AuthUtil authUtil;

    @Override
    public PassengerDTO add(PassengerCreateDTO dto) {
        User customer = authUtil.loadLoggedUser();
        if (passengerRepository.existsByCustomerId(customer.getId())) {
            throw new AlreadyExistException("Passenger details for this user already exists");
        }

        Passenger entity = passengerMapper.toEntity(dto);
        entity.setCustomerId(customer.getId());
        passengerRepository.save(entity);

        return passengerMapper.toDTO(entity);
    }

    @Override
    public Page<PassengerDTO> findPassengersWithReservedSeatsOnFlight(String flightNumber, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Passenger> pageObj = passengerRepository.findPassengersWithReservedSeatsOnFlight(flightNumber, pageable);
        List<PassengerDTO> dtoList = passengerMapper.toDTOList(pageObj.getContent());

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }
}
