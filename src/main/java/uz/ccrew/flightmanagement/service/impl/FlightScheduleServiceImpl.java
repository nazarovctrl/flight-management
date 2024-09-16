package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.dto.flightSchedule.*;
import uz.ccrew.flightmanagement.entity.*;
import uz.ccrew.flightmanagement.repository.*;
import uz.ccrew.flightmanagement.dto.leg.LegDTO;
import uz.ccrew.flightmanagement.mapper.LegMapper;
import uz.ccrew.flightmanagement.dto.reservation.*;
import uz.ccrew.flightmanagement.enums.TravelClassCode;
import uz.ccrew.flightmanagement.exp.BadRequestException;
import uz.ccrew.flightmanagement.mapper.FlightScheduleMapper;
import uz.ccrew.flightmanagement.service.FlightScheduleService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @Override
    public List<OneWayFlightDTO> getOneWayList(FlightListRequestDTO dto) {
        List<OneWayFlightDTO> flightReservationList = new ArrayList<>();

        List<FlightSchedule> flightSchedules = flightScheduleRepository.findOneWay(dto.departureCity(), dto.arrivalCity(), dto.departureDate());
        flightSchedules.parallelStream()
                .map(this::getOneWayFlight)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(flightReservationList::add);

        return flightReservationList;
    }

    @Override
    public List<RoundTripFlightDTO> getRoundTripList(FlightListRequestDTO dto) {
        List<RoundTripFlightDTO> roundTripFlights = new ArrayList<>();

        if (dto.returnDate().isBefore(dto.departureDate())) {
            throw new BadRequestException("Return date must be after departure date");
        }

        List<RoundTrip> roundTrips = flightScheduleRepository.findRoundTrip(dto.departureCity(), dto.arrivalCity(), dto.departureDate(), dto.returnDate());
        roundTrips.parallelStream()
                .map(this::getRoundTripDTO)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(roundTripFlights::add);

        return roundTripFlights;
    }

    private Optional<RoundTripFlightDTO> getRoundTripDTO(RoundTrip roundTrip) {
        Optional<OneWayFlightDTO> flightOptional = getOneWayFlight(roundTrip.flight());
        if (flightOptional.isEmpty()) {
            return Optional.empty();
        }
        Optional<OneWayFlightDTO> retunrFlightOptional = getOneWayFlight(roundTrip.returnFlight());
        if (retunrFlightOptional.isEmpty()) {
            return Optional.empty();
        }
        OneWayFlightDTO flight = flightOptional.get();
        OneWayFlightDTO returnFlight = retunrFlightOptional.get();

        HashMap<TravelClassCode, Long> roundTripCosts = getRoundTripCost(flight.travelClassCostList(), returnFlight.travelClassCostList());
        if (roundTripCosts.isEmpty()) {
            return Optional.empty();
        }

        HashMap<TravelClassCode, Integer> availableSeats = getAvailableSeats(flight.travelClassAvailableSeats(), returnFlight.travelClassAvailableSeats());
        if (availableSeats.isEmpty()) {
            return Optional.empty();
        }

        RoundTripFlightDTO roundTripFlightDTO = RoundTripFlightDTO.builder()
                .flightDTO(flight.flightDTO())
                .returnFlightDTO(returnFlight.flightDTO())
                .travelClassCostList(roundTripCosts)
                .travelClassAvailableSeats(availableSeats)
                .build();
        return Optional.of(roundTripFlightDTO);
    }

    private HashMap<TravelClassCode, Integer> getAvailableSeats(HashMap<TravelClassCode, Integer> flightAvailableSeats, HashMap<TravelClassCode, Integer> returnFlightAvailableSeats) {
        HashMap<TravelClassCode, Integer> availableSeats = new HashMap<>();

        for (Map.Entry<TravelClassCode, Integer> entry : flightAvailableSeats.entrySet()) {
            TravelClassCode travelClassCode = entry.getKey();
            Integer flightAvailableSeat = entry.getValue();

            if (returnFlightAvailableSeats.containsKey(travelClassCode)) {
                Integer returnFlightAvailableSeat = returnFlightAvailableSeats.get(travelClassCode);
                availableSeats.put(travelClassCode, Math.min(flightAvailableSeat, returnFlightAvailableSeat));
            }
        }
        return availableSeats;
    }

    private static HashMap<TravelClassCode, Long> getRoundTripCost(HashMap<TravelClassCode, Long> flightCosts, HashMap<TravelClassCode, Long> returnFlightCosts) {
        HashMap<TravelClassCode, Long> roundTripCosts = new HashMap<>();

        for (Map.Entry<TravelClassCode, Long> entry : flightCosts.entrySet()) {
            TravelClassCode travelClassCode = entry.getKey();
            Long flight1Cost = entry.getValue();

            if (returnFlightCosts.containsKey(travelClassCode)) {
                Long flight2Cost = returnFlightCosts.get(travelClassCode);
                roundTripCosts.put(travelClassCode, flight1Cost + flight2Cost);
            }
        }
        return roundTripCosts;
    }

    private Optional<OneWayFlightDTO> getOneWayFlight(FlightSchedule flight) {
        HashMap<TravelClassCode, Integer> totalSeats = new HashMap<>();
        HashMap<TravelClassCode, Long> costs = new HashMap<>();
        Map<TravelClassCode, Integer> reservedSeats = getReservedSeats(flight.getFlightNumber());

        initializeCostAndTotalSeats(flight.getFlightNumber(), costs, totalSeats);

        HashMap<TravelClassCode, Integer> availableSeats = computeAvailableSeats(totalSeats, reservedSeats);
        if (availableSeats.isEmpty()) {
            return Optional.empty();
        }
        OneWayFlightDTO flightDTO = OneWayFlightDTO.builder()
                .flightDTO(flightScheduleMapper.toDTO(flight))
                .travelClassCostList(costs)
                .travelClassAvailableSeats(availableSeats)
                .build();
        return Optional.of(flightDTO);
    }

    private void initializeCostAndTotalSeats(Long flightNumber, Map<TravelClassCode, Long> costs, HashMap<TravelClassCode, Integer> totalSeats) {
        LocalDate now = LocalDate.now();
        List<FlightCost> flightCosts = flightCostRepository.findByFlightSchedule_FlightNumberAndId_ValidFromDateLessThanEqualAndValidToDateGreaterThanEqual(
                flightNumber, now, now);

        // Process flight costs to accumulate total seats and cost DTOs
        for (FlightCost flightCost : flightCosts) {
            List<TravelClassCapacity> travelClassCapacities = travelClassCapacityRepository.findById_AircraftTypeCode(flightCost.getId().getAircraftTypeCode());

            for (TravelClassCapacity capacity : travelClassCapacities) {
                TravelClassCode travelClassCode = capacity.getId().getTravelClassCode();
                totalSeats.merge(travelClassCode, capacity.getSeatCapacity(), Integer::sum);
                costs.put(travelClassCode, flightCost.getFlightCost());
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
