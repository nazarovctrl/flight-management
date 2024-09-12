package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.dto.airport.AirportCreateDTO;
import uz.ccrew.flightmanagement.dto.airport.AirportDTO;
import uz.ccrew.flightmanagement.entity.Airport;
import uz.ccrew.flightmanagement.entity.User;
import uz.ccrew.flightmanagement.enums.UserRole;
import uz.ccrew.flightmanagement.exp.unauthorized.Unauthorized;
import uz.ccrew.flightmanagement.mapper.AirportMapper;
import uz.ccrew.flightmanagement.repository.AirportRepository;
import uz.ccrew.flightmanagement.service.AirportService;
import uz.ccrew.flightmanagement.util.AuthUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {
    private final AirportRepository airportRepository;
    private final AuthUtil authUtil;
    private final AirportMapper airportMapper;

    @Override
    public AirportDTO addAirport(AirportCreateDTO airportCreateDTO) {
        User user = authUtil.loadLoggedUser();
        if (user.getRole().equals(UserRole.ADMINISTRATOR)) {
            Airport airport = airportMapper.toEntity(airportCreateDTO);
            airportRepository.save(airport);
            return airportMapper.toDTO(airport);
        }
        throw new Unauthorized("Role should be admin");
    }
}
