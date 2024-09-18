package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.entity.Leg;
import uz.ccrew.flightmanagement.dto.leg.LegDTO;
import uz.ccrew.flightmanagement.mapper.LegMapper;
import uz.ccrew.flightmanagement.service.LegService;
import uz.ccrew.flightmanagement.dto.leg.LegUpdateDTO;
import uz.ccrew.flightmanagement.dto.leg.LegCreateDTO;
import uz.ccrew.flightmanagement.entity.FlightSchedule;
import uz.ccrew.flightmanagement.exp.BadRequestException;
import uz.ccrew.flightmanagement.repository.LegRepository;
import uz.ccrew.flightmanagement.exp.AlreadyExistException;
import uz.ccrew.flightmanagement.repository.FlightScheduleRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LegServiceImpl implements LegService {
    private final LegMapper legMapper;
    private final LegRepository legRepository;
    private final FlightScheduleRepository flightScheduleRepository;

    @Override
    public LegDTO add(LegCreateDTO dto) {
        Leg entity = legMapper.toEntity(dto);
        if (entity.getOriginAirport().equals(entity.getDestinationAirport())) {
            throw new BadRequestException("Origin airport and destination airport can not be same");
        }
        if (legRepository.existsByFlightSchedule_FlightNumberAndOriginAirportAndDestinationAirport(dto.flightNumber(), entity.getOriginAirport(), entity.getDestinationAirport())) {
            throw new AlreadyExistException("Leg with this details already exists");
        }

        FlightSchedule flightSchedule = flightScheduleRepository.loadById(dto.flightNumber());
        entity.setFlightSchedule(flightSchedule);
        legRepository.save(entity);

        return legMapper.toDTO(entity);
    }

    @Override
    public LegDTO update(Long legId, LegUpdateDTO dto) {
        Leg entity = legRepository.loadById(legId);
        if (dto.actualArrivalTime().isBefore(dto.actualDepartureTime())) {
            throw new BadRequestException("Arrival time must be after departure time.");
        }

        entity.setActualDepartureTime(dto.actualDepartureTime());
        entity.setActualArrivalTime(dto.actualArrivalTime());
        legRepository.save(entity);

        return legMapper.toDTO(entity);
    }
}
