package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.entity.*;
import uz.ccrew.flightmanagement.repository.*;
import uz.ccrew.flightmanagement.util.AuthUtil;
import uz.ccrew.flightmanagement.enums.TravelClassCode;
import uz.ccrew.flightmanagement.enums.PaymentStatusCode;
import uz.ccrew.flightmanagement.exp.BadRequestException;
import uz.ccrew.flightmanagement.mapper.ReservationMapper;
import uz.ccrew.flightmanagement.service.ReservationService;
import uz.ccrew.flightmanagement.enums.ReservationStatusCode;
import uz.ccrew.flightmanagement.dto.reservation.ReservationDTO;
import uz.ccrew.flightmanagement.dto.reservation.TravelClassSeatDTO;
import uz.ccrew.flightmanagement.dto.reservation.OneWayReservationCreateDTO;

import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final PassengerRepository passengerRepository;
    private final LegRepository legRepository;
    private final FlightScheduleRepository flightScheduleRepository;
    private final ItineraryLegRepository itineraryLegRepository;
    private final FlightCostRepository flightCostRepository;
    private final TravelClassCapacityRepository travelClassCapacityRepository;
    private final PaymentRepository paymentRepository;
    private final AuthUtil authUtil;
    private final ReservationMapper reservationMapper;

    @Transactional
    @Override
    public ReservationDTO makeOneWay(OneWayReservationCreateDTO dto) {
        FlightSchedule flight = flightScheduleRepository.loadById(dto.flightNumber());

        checkToAvailability(dto.flightNumber(), dto.travelClassCode());

        Optional<Passenger> passenger = passengerRepository.findByCustomer_Id(authUtil.loadLoggedUser().getId());
        if (passenger.isEmpty()) {
            throw new BadRequestException("Create passenger before making reservation");
        }
        ItineraryReservation reservation = ItineraryReservation.builder()
                .passenger(passenger.get())
                .reservationStatusCode(ReservationStatusCode.CREATED)
                .dateReservationMade(LocalDateTime.now())
                .ticketTypeCode(dto.ticketTypeCode())
                .travelClassCode(dto.travelClassCode())
                .build();

        reservationRepository.save(reservation);

        List<Leg> legs = legRepository.findAllByFlightSchedule_FlightNumber(flight.getFlightNumber());
        for (Leg leg : legs) {
            ItineraryLeg itineraryLeg = ItineraryLeg.builder()
                    .id(new ItineraryLeg.ItineraryLegId(reservation.getReservationId(), leg.getLegId()))
                    .leg(leg)
                    .reservation(reservation)
                    .build();
            itineraryLegRepository.save(itineraryLeg);
        }

        Payment payment = Payment.builder()
                .paymentAmount(getCost(flight.getFlightNumber(), dto.travelClassCode()))
                .paymentStatusCode(PaymentStatusCode.CREATED)
                .build();
        paymentRepository.save(payment);

        return reservationMapper.toDTO(reservation);
    }

    public Long getCost(Long flightNumber, TravelClassCode classCode) {
        List<FlightCost> flightCosts = flightCostRepository.findByFlightSchedule_FlightNumberAndId_ValidFromDateLessThanEqualAndValidToDateGreaterThanEqual(
                flightNumber, LocalDate.now(), LocalDate.now());

        for (FlightCost flightCost : flightCosts) {
            List<TravelClassCapacity> travelClassCapacities = travelClassCapacityRepository.findById_AircraftTypeCode(flightCost.getId().getAircraftTypeCode());
            for (TravelClassCapacity capacity : travelClassCapacities) {
                TravelClassCode travelClassCode = capacity.getId().getTravelClassCode();
                if (travelClassCode.equals(classCode)) {
                    return flightCost.getFlightCost();
                }
            }
        }
        throw new BadRequestException("FLight cost for given travel class not found");
    }

    private void checkToAvailability(Long flightNumber, TravelClassCode travelClassCode) {
        int legCount = legRepository.countByFlightSchedule_FlightNumber(flightNumber);

        // Retrieve reserved seats for each travel class
        List<TravelClassSeatDTO> travelClassSeatList = itineraryLegRepository.getTravelClassReservedSeatsByFlight(flightNumber, legCount);
        Map<TravelClassCode, Integer> reservedSeats = travelClassSeatList.stream().collect(Collectors.toMap(TravelClassSeatDTO::getTravelClassCode, TravelClassSeatDTO::getReservedSeats));

        int reservedSeatCount = reservedSeats.get(travelClassCode);

        List<FlightCost> flightCosts = flightCostRepository.findByFlightSchedule_FlightNumberAndId_ValidFromDateLessThanEqualAndValidToDateGreaterThanEqual(
                flightNumber, LocalDate.now(), LocalDate.now());

        int totalSeatCount = 0;
        // Process flight costs to accumulate total seats and cost DTOs
        for (FlightCost flightCost : flightCosts) {
            List<TravelClassCapacity> travelClassCapacities = travelClassCapacityRepository.findById_AircraftTypeCode(flightCost.getId().getAircraftTypeCode());

            for (TravelClassCapacity capacity : travelClassCapacities) {
                if (capacity.getId().getTravelClassCode().equals(travelClassCode)) {
                    totalSeatCount = capacity.getSeatCapacity();
                    break;
                }
            }
        }

        if (reservedSeatCount >= totalSeatCount) {
            throw new BadRequestException("There is no available seat for this travel class code");
        }
    }
}