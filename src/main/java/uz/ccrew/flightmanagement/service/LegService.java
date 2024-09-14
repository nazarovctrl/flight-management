package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.dto.leg.LegDTO;
import uz.ccrew.flightmanagement.dto.leg.LegCreateDTO;

public interface LegService {
    LegDTO add(LegCreateDTO dto);
}
