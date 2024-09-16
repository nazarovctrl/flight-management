package uz.ccrew.flightmanagement.mapper;

import uz.ccrew.flightmanagement.entity.TravelClassCapacity;
import uz.ccrew.flightmanagement.dto.travelclasscapacity.TravelClassCapacityDTO;
import uz.ccrew.flightmanagement.dto.travelclasscapacity.TravelClassCapacityCreateDTO;

import org.springframework.stereotype.Component;

@Component
public class TravelClassCapacityMapper implements Mapper<TravelClassCapacityCreateDTO, TravelClassCapacityDTO, TravelClassCapacity> {
    @Override
    public TravelClassCapacity toEntity(TravelClassCapacityCreateDTO dto) {
        return TravelClassCapacity.builder()
                .id(new TravelClassCapacity.TravelClassCapacityId(dto.aircraftTypeCode(), dto.travelClassCode()))
                .seatCapacity(dto.seatCapacity())
                .build();
    }

    @Override
    public TravelClassCapacityDTO toDTO(TravelClassCapacity entity) {
        return TravelClassCapacityDTO.builder()
                .aircraftTypeCode(entity.getId().getAircraftTypeCode())
                .travelClassCode(entity.getId().getTravelClassCode())
                .seatCapacity(entity.getSeatCapacity())
                .build();
    }
}
