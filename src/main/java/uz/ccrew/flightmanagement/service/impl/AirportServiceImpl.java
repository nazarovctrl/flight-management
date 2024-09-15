package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.dto.airport.AirportCreateDTO;
import uz.ccrew.flightmanagement.dto.airport.AirportDTO;
import uz.ccrew.flightmanagement.entity.Airport;
import uz.ccrew.flightmanagement.exp.AlreadyExistException;
import uz.ccrew.flightmanagement.mapper.AirportMapper;
import uz.ccrew.flightmanagement.repository.AirportRepository;
import uz.ccrew.flightmanagement.service.AirportService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {
    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;

    @Override
    public AirportDTO addAirport(AirportCreateDTO airportCreateDTO) {
        Optional<Airport> optionalAirport = airportRepository.findByAirportCode(airportCreateDTO.airportCode());

        if (optionalAirport.isPresent()) {
            throw new AlreadyExistException("This Airport already exist");
        }
        Airport airport = airportMapper.toEntity(airportCreateDTO);
        airportRepository.save(airport);
        return airportMapper.toDTO(airport);
    }

    @Override
    public List<String> getCityList() {
        return airportRepository.getCityList();
    }
}
