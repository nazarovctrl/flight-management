package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleCreateDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleDTO;
import uz.ccrew.flightmanagement.entity.Airport;
import uz.ccrew.flightmanagement.entity.FlightSchedule;
import uz.ccrew.flightmanagement.exp.AircraftAlreadyBookedException;
import uz.ccrew.flightmanagement.exp.NotFoundException;
import uz.ccrew.flightmanagement.mapper.FlightScheduleMapper;
import uz.ccrew.flightmanagement.repository.AirportRepository;
import uz.ccrew.flightmanagement.repository.FlightScheduleRepository;
import uz.ccrew.flightmanagement.service.FlightScheduleService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightScheduleServiceImpl implements FlightScheduleService {
    private final FlightScheduleRepository flightScheduleRepository;
    private final FlightScheduleMapper flightScheduleMapper;
    private final AirportRepository airportRepository;

    @Override
    public FlightScheduleDTO addFlightSchedule(FlightScheduleCreateDTO flightScheduleCreateDTO) {
        // Находим аэропорты по кодам
        Airport originAirport = airportRepository.findById(flightScheduleCreateDTO.originAirportCode())
                .orElseThrow(() -> new NotFoundException("Origin airport not found"));
        Airport destinationAirport = airportRepository.findById(flightScheduleCreateDTO.destinationAirportCode())
                .orElseThrow(() -> new NotFoundException("Destination airport not found"));

        // Проверяем, доступен ли самолет
        boolean isAircraftAvailable = flightScheduleRepository
                .existsByUsualAircraftTypeCodeAndDepartureDateTimeBeforeAndArrivalDateTimeAfter(
                        flightScheduleCreateDTO.usualAircraftTypeCode(),
                        flightScheduleCreateDTO.arrivalDateTime(),
                        flightScheduleCreateDTO.departureDateTime());

        if (isAircraftAvailable) {
            throw new AircraftAlreadyBookedException("Airplane is already booked");
        }

        // Создаем объект FlightSchedule
        FlightSchedule flightSchedule = FlightSchedule.builder()
                .airlineCode(flightScheduleCreateDTO.airlineCode())
                .usualAircraftTypeCode(flightScheduleCreateDTO.usualAircraftTypeCode())
                .originAirport(originAirport)
                .destinationAirport(destinationAirport)
                .departureDateTime(flightScheduleCreateDTO.departureDateTime())
                .arrivalDateTime(flightScheduleCreateDTO.arrivalDateTime())
                .build();

        // Сохраняем расписание
        flightScheduleRepository.save(flightSchedule);

        return FlightScheduleDTO.builder()
                .flightNumber(flightSchedule.getFlightNumber())
                .airlineCode(flightSchedule.getAirlineCode())
                .usualAircraftTypeCode(flightSchedule.getUsualAircraftTypeCode())
                .originAirportCode(originAirport.getAirportCode())
                .destinationAirportCode(destinationAirport.getAirportCode())
                .departureDateTime(flightSchedule.getDepartureDateTime())
                .arrivalDateTime(flightSchedule.getArrivalDateTime())
                .build();
    }


    @Override
    public void delete(Long flightNumber) {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(flightNumber)
                .orElseThrow(() -> new NotFoundException("Flight Schedule not found"));
        flightScheduleRepository.delete(flightSchedule);
    }
}


