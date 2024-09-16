package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.dto.reservation.*;
import uz.ccrew.flightmanagement.entity.*;
import uz.ccrew.flightmanagement.repository.*;
import uz.ccrew.flightmanagement.dto.leg.LegDTO;
import uz.ccrew.flightmanagement.mapper.LegMapper;
import uz.ccrew.flightmanagement.enums.TravelClassCode;
import uz.ccrew.flightmanagement.exp.BadRequestException;
import uz.ccrew.flightmanagement.mapper.FlightScheduleMapper;
import uz.ccrew.flightmanagement.service.FlightScheduleService;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleReportDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleCreateDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightScheduleServiceImpl implements FlightScheduleService {
    private final LegMapper legMapper;
    private final LegRepository legRepository;
    private final AirportRepository airportRepository;
    private final FlightScheduleMapper flightScheduleMapper;
    private final FlightCostRepository flightCostRepository;
    private final ItineraryLegRepository itineraryLegRepository;
    private final FlightScheduleRepository flightScheduleRepository;
    private final TravelClassCapacityRepository travelClassCapacityRepository;

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

    public List<FlightReservationDTO> getOneWayList(ReservationRequestDTO dto) {
        List<FlightReservationDTO> flightReservationList = new ArrayList<>();

        List<FlightSchedule> flightSchedules = flightScheduleRepository.findOneWay(dto.departureCity(), dto.arrivalCity(), dto.departureDate());
        for (FlightSchedule flight : flightSchedules) {
            HashMap<TravelClassCode, Integer> totalSeats = new HashMap<>();
            List<TravelClassCostDTO> costList = new ArrayList<>();
            Map<TravelClassCode, Integer> reservedSeats = getReservedSeats(flight.getFlightNumber());

            initializeCostAndTotalSeats(flight.getFlightNumber(), costList, totalSeats);

            HashMap<TravelClassCode, Integer> availableSeats = computeAvailableSeats(totalSeats, reservedSeats);
            if (availableSeats.isEmpty()) {
                continue;
            }

            FlightReservationDTO flightReservationDTO = FlightReservationDTO.builder()
                    .flightDTO(flightScheduleMapper.toDTO(flight))
                    .travelClassCostList(costList)
                    .travelClassAvailableSeats(availableSeats)
                    .build();

            flightReservationList.add(flightReservationDTO);
        }

        return flightReservationList;
    }

    @Override
    public List<FlightReservationDTO> getRoundTripList(ReservationRequestDTO dto) {
        List<FlightReservationDTO> flightReservationList = new ArrayList<>();

        if (dto.returnDate().isBefore(dto.departureDate())) {
            throw new BadRequestException("Return date must be after departure date");
        }

        List<RoundTrip> roundTrips = flightScheduleRepository.findRoundTrip(dto.departureCity(), dto.arrivalCity(), dto.departureDate(), dto.returnDate());

        return flightReservationList;
    }


    private void initializeCostAndTotalSeats(Long flightNumber, List<TravelClassCostDTO> costList, HashMap<TravelClassCode, Integer> totalSeats) {
        LocalDate now = LocalDate.now();
        List<FlightCost> flightCosts = flightCostRepository.findByFlightSchedule_FlightNumberAndId_ValidFromDateLessThanEqualAndValidToDateGreaterThanEqual(
                flightNumber, now, now);

        // Process flight costs to accumulate total seats and cost DTOs
        for (FlightCost flightCost : flightCosts) {
            List<TravelClassCapacity> travelClassCapacities = travelClassCapacityRepository.findById_AircraftTypeCode(flightCost.getId().getAircraftTypeCode());

            for (TravelClassCapacity capacity : travelClassCapacities) {
                TravelClassCode travelClassCode = capacity.getId().getTravelClassCode();
                totalSeats.merge(travelClassCode, capacity.getSeatCapacity(), Integer::sum);
                costList.add(new TravelClassCostDTO(travelClassCode, flightCost.getFlightCost()));
            }
        }
    }

    private Map<TravelClassCode, Integer> getReservedSeats(Long flightNumber) {
        int legCount = legRepository.countByFlightSchedule_FlightNumber(flightNumber);

        List<TravelClassSeatDTO> travelClassSeatList = itineraryLegRepository.getTravelClassReservedSeatsByFlight(flightNumber, legCount);
        return travelClassSeatList.stream().collect(Collectors.toMap(TravelClassSeatDTO::getTravelClassCode, TravelClassSeatDTO::getReservedSeats));
    }

    private HashMap<TravelClassCode, Integer> computeAvailableSeats(HashMap<TravelClassCode, Integer> totalSeats, Map<TravelClassCode, Integer> reservedSeats) {
        HashMap<TravelClassCode, Integer> availableSeats = new HashMap<>();
        for (Map.Entry<TravelClassCode, Integer> entry : totalSeats.entrySet()) {
            TravelClassCode travelClassCode = entry.getKey();
            int total = entry.getValue();
            int reserved = reservedSeats.getOrDefault(travelClassCode, 0);
            int available = total - reserved;
            if (available < 1) {
                continue;
            }
            availableSeats.put(travelClassCode, available);
        }
        return availableSeats;
    }
}
