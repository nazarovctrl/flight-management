package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.entity.FlightCost;
import uz.ccrew.flightmanagement.entity.FlightSchedule;
import uz.ccrew.flightmanagement.entity.RefCalendar;
import uz.ccrew.flightmanagement.mapper.FlightCostMapper;
import uz.ccrew.flightmanagement.repository.FlightScheduleRepository;
import uz.ccrew.flightmanagement.repository.RefCalendarRepository;
import uz.ccrew.flightmanagement.service.FlightCostService;
import uz.ccrew.flightmanagement.dto.flightcost.FlightCostDTO;
import uz.ccrew.flightmanagement.repository.FlightCostRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightCostServiceImpl implements FlightCostService {
    private final FlightCostRepository flightCostRepository;
    private final FlightScheduleRepository flightScheduleRepository;
    private final RefCalendarRepository refCalendarRepository;
    private final FlightCostMapper fLightCostMapper;

    @Override
    public FlightCostDTO save(FlightCostDTO dto) {
        FlightSchedule flightSchedule = flightScheduleRepository.loadById(dto.flightNumber());
        RefCalendar validFromRefCalender = refCalendarRepository.loadById(dto.validFromDate());

        FlightCost entity = fLightCostMapper.toEntity(dto);
        entity.setFlightSchedule(flightSchedule);
        entity.setValidFromRefCalendar(validFromRefCalender);

        //TODO check flightNumber validFromDate validToDate to exist
        flightCostRepository.save(entity);
        return fLightCostMapper.toDTO(entity);
    }
}
