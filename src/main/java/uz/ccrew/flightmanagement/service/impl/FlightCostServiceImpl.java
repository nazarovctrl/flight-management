package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.entity.FlightCost;
import uz.ccrew.flightmanagement.mapper.FLightCostMapper;
import uz.ccrew.flightmanagement.service.FlightCostService;
import uz.ccrew.flightmanagement.dto.flightcost.FlightCostDTO;
import uz.ccrew.flightmanagement.repository.FlightCostRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightCostServiceImpl implements FlightCostService {
    private final FlightCostRepository flightCostRepository;
    private final FLightCostMapper fLightCostMapper;

    @Override
    public FlightCostDTO save(FlightCostDTO dto) {
        FlightCost entity = fLightCostMapper.toEntity(dto);
        //TODO check flightNumber validFromDate validToDate to exist
        flightCostRepository.save(entity);
        return fLightCostMapper.toDTO(entity);
    }
}
