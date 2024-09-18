package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.entity.*;
import uz.ccrew.flightmanagement.repository.*;
import uz.ccrew.flightmanagement.dto.leg.LegDTO;
import uz.ccrew.flightmanagement.mapper.LegMapper;
import uz.ccrew.flightmanagement.dto.flightSchedule.*;
import uz.ccrew.flightmanagement.exp.BadRequestException;
import uz.ccrew.flightmanagement.mapper.FlightScheduleMapper;
import uz.ccrew.flightmanagement.service.OneWayFlightService;
import uz.ccrew.flightmanagement.service.FlightScheduleService;
import uz.ccrew.flightmanagement.service.MultiCityFlightService;
import uz.ccrew.flightmanagement.service.RoundTripFlightService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FlightScheduleServiceImpl implements FlightScheduleService {
    private final LegMapper legMapper;
    private final LegRepository legRepository;
    private final AirportRepository airportRepository;
    private final OneWayFlightService oneWayFlightService;
    private final FlightScheduleMapper flightScheduleMapper;
    private final RoundTripFlightService roundTripFlightService;
    private final MultiCityFlightService multiCityFlightService;
    private final FlightScheduleRepository flightScheduleRepository;

    @Override
    public FlightScheduleDTO addFlightSchedule(FlightScheduleCreateDTO dto) {
        Airport originAirport = airportRepository.loadById(dto.originAirportCode());
        Airport destinationAirport = airportRepository.loadById(dto.destinationAirportCode());

        if (originAirport.getAirportCode().equals(destinationAirport.getAirportCode())) {
            throw new BadRequestException("Origin airport and destination airport can not be same");
        }
        if (dto.arrivalDateTime().isBefore(dto.departureDateTime())) {
            throw new BadRequestException("Arrival time must be after departure time");
        }

        FlightSchedule flightSchedule = flightScheduleMapper.toEntity(dto);

        flightSchedule.setOriginAirport(originAirport);
        flightSchedule.setDestinationAirport(destinationAirport);

        flightScheduleRepository.save(flightSchedule);
        return flightScheduleMapper.toDTO(flightSchedule);
    }

    @Override
    public void delete(Long flightNumber) {
        FlightSchedule flightSchedule = flightScheduleRepository.loadById(flightNumber);
        flightScheduleRepository.delete(flightSchedule);
    }

    @Override
    public Page<FlightScheduleDTO> getList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page<FlightSchedule> pageObj = flightScheduleRepository.findAll(pageable);
        List<FlightScheduleDTO> dtoList = flightScheduleMapper.toDTOList(pageObj.getContent());

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }

    @Override
    public FlightScheduleDTO getFlightSchedule(Long flightNumber) {
        FlightSchedule flightSchedule = flightScheduleRepository.loadById(flightNumber);
        List<Leg> legs = legRepository.findAllByFlightSchedule_FlightNumber(flightNumber);

        List<LegDTO> legDTOs = legMapper.toDTOList(legs);

        return flightScheduleMapper.toDTO(flightSchedule, legDTOs);
    }

    @Override
    public Page<FlightScheduleDTO> getAllFlightSchedulesByAirportCode(String airportCode, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<FlightSchedule> pageObj = flightScheduleRepository.findByAirportCode(airportCode, pageable);
        List<FlightScheduleDTO> dtoList = flightScheduleMapper.toDTOList(pageObj.getContent());

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }

    @Override
    public Page<FlightScheduleReportDTO> getOnTimeFlights(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("departureDateTime").descending());
        Page<FlightScheduleReportDTO> pageObjDelayed = flightScheduleRepository.findOnTimeFlights(pageable);

        return new PageImpl<>(pageObjDelayed.getContent(), pageable, pageObjDelayed.getTotalElements());
    }

    @Override
    public Page<FlightScheduleReportDTO> getDelayedFlights(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("departureDateTime").descending());
        Page<FlightScheduleReportDTO> pageObjDelayed = flightScheduleRepository.findDelayedFlights(pageable);

        return new PageImpl<>(pageObjDelayed.getContent(), pageable, pageObjDelayed.getTotalElements());
    }

    @Override
    public List<OneWayFlightDTO> getOneWayList(FlightListRequestDTO dto) {
        if (dto.departureDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Departure date can be minimum today");
        }

        return oneWayFlightService.getOneWayFlights(dto);
    }

    @Override
    public List<RoundTripFlightDTO> getRoundTripList(FlightListRequestDTO dto) {
        if (dto.departureDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Departure date can be minimum today");
        }
        if (dto.returnDate().isBefore(dto.departureDate())) {
            throw new BadRequestException("Return date must be after departure date");
        }

        return roundTripFlightService.getRoundTripFlights(dto);
    }

    @Override
    public List<MultiCityFlightDTO> getMultiCityTrip(FlightListRequestDTO dto) {
        if (dto.departureDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Departure date can be minimum today");
        }
        if (dto.maxStops() < 2) {
            throw new BadRequestException("Max stops minimum value is 3");
        }

        Optional<Airport> originAirportOptional = airportRepository.findFirstByCity(dto.departureCity());
        Optional<Airport> destinationAirportOptional = airportRepository.findFirstByCity(dto.arrivalCity());
        if (originAirportOptional.isEmpty() || destinationAirportOptional.isEmpty()) {
            throw new BadRequestException("Departure or arrival city are not found");
        }

        return multiCityFlightService.getMultiCityFlights(dto);
    }
}
