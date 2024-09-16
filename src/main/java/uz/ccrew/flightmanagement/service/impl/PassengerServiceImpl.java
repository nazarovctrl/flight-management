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

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
