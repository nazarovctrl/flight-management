package uz.ccrew.flightmanagement.mapper;

import uz.ccrew.flightmanagement.entity.RefCalendar;
import uz.ccrew.flightmanagement.dto.refcalendar.RefCalendarDTO;

import org.springframework.stereotype.Component;

@Component
public class RefCalendarMapper implements Mapper<RefCalendarDTO, RefCalendarDTO, RefCalendar> {
    @Override
    public RefCalendar toEntity(RefCalendarDTO dto) {
        return RefCalendar.builder()
                .dayDate(dto.dayDate())
                .dayNumber(dto.dayNumber())
                .businessDayYn(dto.businessDayYn())
                .build();
    }

    @Override
    public RefCalendarDTO toDTO(RefCalendar refCalendar) {
        return RefCalendarDTO.builder()
                .dayDate(refCalendar.getDayDate())
                .dayNumber(refCalendar.getDayNumber())
                .businessDayYn(refCalendar.getBusinessDayYn())
                .build();
    }
}
