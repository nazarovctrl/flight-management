package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.entity.RefCalendar;
import uz.ccrew.flightmanagement.mapper.RefCalendarMapper;
import uz.ccrew.flightmanagement.service.RefCalendarService;
import uz.ccrew.flightmanagement.dto.refcalendar.RefCalendarDTO;
import uz.ccrew.flightmanagement.repository.RefCalendarRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefCalendarServiceImpl implements RefCalendarService {
    private final RefCalendarMapper refCalendarMapper;
    private final RefCalendarRepository refCalendarRepository;

    @Override
    public RefCalendarDTO save(RefCalendarDTO dto) {
        RefCalendar entity = refCalendarMapper.toEntity(dto);
        refCalendarRepository.save(entity);
        return refCalendarMapper.toDTO(entity);
    }
}
