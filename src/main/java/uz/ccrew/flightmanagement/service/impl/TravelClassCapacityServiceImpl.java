package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.entity.TravelClassCapacity;
import uz.ccrew.flightmanagement.mapper.TravelClassCapacityMapper;
import uz.ccrew.flightmanagement.service.TravelClassCapacityService;
import uz.ccrew.flightmanagement.repository.TravelClassCapacityRepository;
import uz.ccrew.flightmanagement.dto.travelclasscapacity.TravelClassCapacityDTO;
import uz.ccrew.flightmanagement.dto.travelclasscapacity.TravelClassCapacityCreateDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelClassCapacityServiceImpl implements TravelClassCapacityService {
    private final TravelClassCapacityMapper travelClassCapacityMapper;
    private final TravelClassCapacityRepository travelClassCapacityRepository;

    @Override
    public TravelClassCapacityDTO add(TravelClassCapacityCreateDTO dto) {
        TravelClassCapacity entity = travelClassCapacityMapper.toEntity(dto);
        travelClassCapacityRepository.save(entity);
        return travelClassCapacityMapper.toDTO(entity);
    }
}
