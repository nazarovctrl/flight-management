package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.entity.*;
import uz.ccrew.flightmanagement.service.*;
import uz.ccrew.flightmanagement.repository.*;
import uz.ccrew.flightmanagement.util.AuthUtil;
import uz.ccrew.flightmanagement.enums.TravelClassCode;
import uz.ccrew.flightmanagement.enums.PaymentStatusCode;
import uz.ccrew.flightmanagement.exp.BadRequestException;
import uz.ccrew.flightmanagement.mapper.ReservationMapper;
import uz.ccrew.flightmanagement.enums.ReservationStatusCode;
import uz.ccrew.flightmanagement.dto.reservation.ReservationDTO;
import uz.ccrew.flightmanagement.dto.reservation.TravelClassSeatDTO;
import uz.ccrew.flightmanagement.dto.reservation.OneWayReservationCreateDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final AuthUtil authUtil;
    private final LegRepository legRepository;
    private final PassengerService passengerService;
    private final ReservationMapper reservationMapper;
    private final PaymentRepository paymentRepository;
    private final ItineraryLegService itineraryLegService;
    private final FlightCostRepository flightCostRepository;
    private final ReservationRepository reservationRepository;
    private final ItineraryLegRepository itineraryLegRepository;
    private final FlightScheduleRepository flightScheduleRepository;
    private final ReservationPaymentRepository reservationPaymentRepository;
    private final TravelClassCapacityRepository travelClassCapacityRepository;

    @Transactional
    @Override
    public ReservationDTO makeOneWay(OneWayReservationCreateDTO dto) {
        FlightSchedule flight = flightScheduleRepository.loadById(dto.flightNumber());

        checkToAvailability(dto.travelClassCode(), dto.flightNumber());

        Passenger passenger = passengerService.getPassenger(dto.passenger());

        ItineraryReservation reservation = ItineraryReservation.builder()
                .passenger(passenger)
                .reservationStatusCode(ReservationStatusCode.CREATED)
                .dateReservationMade(LocalDateTime.now())
                .ticketTypeCode(dto.ticketTypeCode())
                .travelClassCode(dto.travelClassCode())
                .build();
        reservationRepository.save(reservation);

        itineraryLegService.addItineraryLegs(reservation, flight.getFlightNumber());

        Long paymentAmount = getCost(dto.travelClassCode(), flight.getFlightNumber());
        addPayment(reservation, paymentAmount);

        return reservationMapper.toDTO(reservation);
    }

    @Override
    public void checkToAvailabilityWithReservationId(Long reservationId, TravelClassCode travelClassCode) {
        Optional<FlightSchedule> flight = itineraryLegRepository.findFlightByReservationId(reservationId);
        if (flight.isEmpty()) {
            throw new BadRequestException("Reservation flight is invalid");
        }
        checkToAvailability(travelClassCode, flight.get().getFlightNumber());
    }

    @Override
    public void reverseReservation(Long reservationId) {
        Optional<FlightSchedule> optionalFlight = itineraryLegRepository.findFlightByReservationId(reservationId);
        if (optionalFlight.isEmpty()) {
            return;
        }
        FlightSchedule flight = optionalFlight.get();
        if (flight.getDepartureDateTime().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new BadRequestException("Reservation can be reverse before 1 hour departure time");
        }
        itineraryLegRepository.deleteByReservation_ReservationId(reservationId);
    }

    @Transactional
    @Override
    public ReservationDTO cancel(Long reservationId) {
        ItineraryReservation reservation = reservationRepository.loadById(reservationId);
        if (!reservation.getReservationStatusCode().equals(ReservationStatusCode.CREATED)) {
            throw new BadRequestException("Reservation status must be CREATED to cancel");
        }
        reservation.setReservationStatusCode(ReservationStatusCode.CANCELED);
        reservationRepository.save(reservation);
        itineraryLegRepository.deleteByReservation_ReservationId(reservationId);
        return reservationMapper.toDTO(reservation);
    }

    @Override
    public Page<ReservationDTO> getList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateReservationMade").descending());

        Page<ItineraryReservation> pageObj = reservationRepository.findByPassenger_CustomerId(authUtil.loadLoggedUser().getId(), pageable);
        List<ReservationDTO> dtoList = reservationMapper.toDTOList(pageObj.getContent());

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }

    private Long getCost(TravelClassCode classCode, Long flightNumber) {
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

    private void checkToAvailability(TravelClassCode travelClassCode, Long flightNumber) {
        int legCount = legRepository.countByFlightSchedule_FlightNumber(flightNumber);

        // Retrieve reserved seats for each travel class
        List<TravelClassSeatDTO> travelClassSeatList = itineraryLegRepository.getTravelClassReservedSeatsByFlight(flightNumber, legCount);
        Map<TravelClassCode, Integer> reservedSeats = travelClassSeatList.stream().collect(Collectors.toMap(TravelClassSeatDTO::getTravelClassCode, TravelClassSeatDTO::getReservedSeats));

        int reservedSeatCount = reservedSeats.getOrDefault(travelClassCode, 0);

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

    private void addPayment(ItineraryReservation reservation, Long paymentAmount) {
        Payment payment = Payment.builder()
                .paymentAmount(paymentAmount)
                .paymentStatusCode(PaymentStatusCode.CREATED)
                .build();
        paymentRepository.save(payment);

        ReservationPayment reservationPayment = ReservationPayment.builder()
                .id(new ReservationPayment.ReservationPaymentId(reservation.getReservationId(), payment.getPaymentId()))
                .payment(payment)
                .reservation(reservation)
                .build();
        reservationPaymentRepository.save(reservationPayment);
    }
}
