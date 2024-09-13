package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.dto.airport.AirportCreateDTO;
import uz.ccrew.flightmanagement.dto.airport.AirportDTO;
import uz.ccrew.flightmanagement.entity.Airport;
import uz.ccrew.flightmanagement.entity.User;
import uz.ccrew.flightmanagement.exp.AlreadyExistException;
import uz.ccrew.flightmanagement.mapper.AirportMapper;
import uz.ccrew.flightmanagement.repository.AirportRepository;
import uz.ccrew.flightmanagement.service.AirportService;
import uz.ccrew.flightmanagement.util.AuthUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {
    private final AirportRepository airportRepository;
    private final AuthUtil authUtil;
    private final AirportMapper airportMapper;

    @Override
    public AirportDTO addAirport(AirportCreateDTO airportCreateDTO) {
        User user = authUtil.loadLoggedUser();

        Optional<Airport> optionalAirport = airportRepository.findByAirportCode(airportCreateDTO.airportCode());

        if (optionalAirport.isEmpty()) {
            Airport airport = airportMapper.toEntity(airportCreateDTO);
            airportRepository.save(airport);
            return airportMapper.toDTO(airport);
        }
        throw new AlreadyExistException("This Airport already exist");
    }
}
