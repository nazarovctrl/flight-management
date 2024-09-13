package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleCreateDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleDTO;
import uz.ccrew.flightmanagement.entity.Airport;
import uz.ccrew.flightmanagement.entity.FlightSchedule;
import uz.ccrew.flightmanagement.exp.AircraftAlreadyBookedException;
import uz.ccrew.flightmanagement.exp.NotFoundException;
import uz.ccrew.flightmanagement.mapper.FlightScheduleMapper;
import uz.ccrew.flightmanagement.repository.AirportRepository;
import uz.ccrew.flightmanagement.repository.FlightScheduleRepository;
import uz.ccrew.flightmanagement.service.FlightScheduleService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightScheduleServiceImpl implements FlightScheduleService {
    private final FlightScheduleRepository flightScheduleRepository;
    private final FlightScheduleMapper flightScheduleMapper;
    private final AirportRepository airportRepository;

    @Override
    public FlightScheduleDTO addFlightSchedule(FlightScheduleCreateDTO flightScheduleCreateDTO) {
        Airport originAirport = airportRepository.findById(flightScheduleCreateDTO.originAirportCode())
                .orElseThrow(() -> new NotFoundException("Origin airport not found"));
        Airport destinationAirport = airportRepository.findById(flightScheduleCreateDTO.destinationAirportCode())
                .orElseThrow(() -> new NotFoundException("Destination airport not found"));

        FlightSchedule flightSchedule = flightScheduleMapper.toEntity(flightScheduleCreateDTO);
        flightSchedule.setOriginAirport(originAirport);
        flightSchedule.setDestinationAirport(destinationAirport);

        flightScheduleRepository.save(flightSchedule);
        return flightScheduleMapper.toDTO(flightSchedule);
    }


    @Override
    public void delete(Long flightNumber) {
        FlightSchedule flightSchedule = flightScheduleRepository.loadById(flightNumber);
        flightScheduleRepository.delete(flightSchedule);
    }
}


