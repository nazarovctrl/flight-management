package uz.ccrew.flightmanagement.mapper;

import uz.ccrew.flightmanagement.entity.FlightCost;
import uz.ccrew.flightmanagement.dto.flightcost.FlightCostDTO;

import org.springframework.stereotype.Component;

@Component
public class FLightCostMapper implements Mapper<FlightCostDTO, FlightCostDTO, FlightCost> {

    @Override
    public FlightCost toEntity(FlightCostDTO dto) {
        return FlightCost.builder()
                .id(new FlightCost.FlightCostsId(dto.flightNumber(), dto.aircraftTypeCode(), dto.validFromDate()))
                .validToDate(dto.validToDate())
                .build();
    }

    @Override
    public FlightCostDTO toDTO(FlightCost entity) {
        return FlightCostDTO.builder()
                .flightNumber(entity.getId().getFlightNumber())
                .validFromDate(entity.getId().getValidFromDate())
                .build();
    }
}
