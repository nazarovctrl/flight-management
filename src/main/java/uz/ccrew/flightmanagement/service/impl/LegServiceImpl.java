package uz.ccrew.flightmanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ccrew.flightmanagement.dto.leg.LegCreateDTO;
import uz.ccrew.flightmanagement.dto.leg.LegDTO;
import uz.ccrew.flightmanagement.entity.FlightSchedule;
import uz.ccrew.flightmanagement.entity.Leg;
import uz.ccrew.flightmanagement.mapper.LegMapper;
import uz.ccrew.flightmanagement.repository.FlightScheduleRepository;
import uz.ccrew.flightmanagement.repository.LegRepository;
import uz.ccrew.flightmanagement.service.LegService;

@Service
@RequiredArgsConstructor
public class LegServiceImpl implements LegService {
    private final LegRepository legRepository;
    private final FlightScheduleRepository flightScheduleRepository;
    private final LegMapper legMapper;

    @Override
    public LegDTO add(LegCreateDTO dto) {
        FlightSchedule flightSchedule = flightScheduleRepository.loadById(dto.flightNumber());

        Leg entity = legMapper.toEntity(dto);
        entity.setFlightSchedule(flightSchedule);
        legRepository.save(entity);

        return legMapper.toDTO(entity);
    }
}
