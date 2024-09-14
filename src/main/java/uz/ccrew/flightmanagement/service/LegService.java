package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.dto.leg.LegDTO;
import uz.ccrew.flightmanagement.dto.leg.LegCreateDTO;
import uz.ccrew.flightmanagement.dto.leg.LegUpdateDTO;

public interface LegService {
    LegDTO add(LegCreateDTO dto);
    LegDTO update(Long legId,LegUpdateDTO dto);
}
