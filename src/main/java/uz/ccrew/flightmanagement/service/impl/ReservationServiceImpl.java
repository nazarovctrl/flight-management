package uz.ccrew.flightmanagement.service.impl;

import uz.ccrew.flightmanagement.dto.passenger.PassengerDTO;
import uz.ccrew.flightmanagement.dto.reservation.*;
import uz.ccrew.flightmanagement.entity.*;
import uz.ccrew.flightmanagement.mapper.FlightCostMapper;
import uz.ccrew.flightmanagement.mapper.PassengerMapper;
import uz.ccrew.flightmanagement.service.*;
import uz.ccrew.flightmanagement.repository.*;
import uz.ccrew.flightmanagement.util.AuthUtil;
import uz.ccrew.flightmanagement.util.RandomUtil;
import uz.ccrew.flightmanagement.enums.TravelClassCode;
import uz.ccrew.flightmanagement.enums.PaymentStatusCode;
import uz.ccrew.flightmanagement.exp.BadRequestException;
import uz.ccrew.flightmanagement.mapper.ReservationMapper;
import uz.ccrew.flightmanagement.mapper.FlightScheduleMapper;
import uz.ccrew.flightmanagement.enums.ReservationStatusCode;
import uz.ccrew.flightmanagement.dto.flightSchedule.RoundTrip;
import uz.ccrew.flightmanagement.dto.flightSchedule.OneWayFlightDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.FlightScheduleDTO;
import uz.ccrew.flightmanagement.dto.flightSchedule.RoundTripFlightDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final AuthUtil authUtil;
    private final RandomUtil randomUtil;
    private final UserRepository userRepository;
    private final PassengerService passengerService;
    private final ReservationMapper reservationMapper;
    private final PassengerMapper passengerMapper;
    private final PaymentRepository paymentRepository;
    private final ItineraryLegService itineraryLegService;
    private final FlightScheduleMapper flightScheduleMapper;
    private final FlightScheduleService flightScheduleService;
    private final ReservationRepository reservationRepository;
    private final ItineraryLegRepository itineraryLegRepository;
    private final BookingAgentRepository bookingAgentRepository;
    private final FlightScheduleRepository flightScheduleRepository;
    private final ReservationPaymentRepository reservationPaymentRepository;
    private final FlightCostRepository flightCostRepository;
    private final TravelClassCapacityRepository travelClassCapacityRepository;
    private final AirportRepository airportRepository;
    private final PassengerRepository passengerRepository;
    private final FlightCostMapper flightCostMapper;
    private final LegRepository legRepository;

    @Transactional
    @Override
    public ReservationDTO makeOneWay(OneWayReservationCreateDTO dto) {
        MainDTO mainDTO = dto.main();
        FlightSchedule flight = flightScheduleRepository.loadById(dto.flightNumber());

        BookingAgent bookingAgent = bookingAgentRepository.loadById(mainDTO.bookingAgentId());

        OneWayFlightDTO oneWayFlight = flightScheduleService.getOneWayFlight(flight)
                .orElseThrow(() -> new BadRequestException("Invalid one way flight"));
        //check
        checkToAvailability(oneWayFlight.travelClassAvailableSeats(), oneWayFlight.travelClassCostList(), mainDTO.travelClassCode());

        Long paymentAmount = oneWayFlight.travelClassCostList().get(mainDTO.travelClassCode());
        Passenger passenger = passengerService.getPassenger(mainDTO.passenger());

        ItineraryReservation reservation = makeReservation(bookingAgent, passenger, paymentAmount, mainDTO, flight.getFlightNumber());

        return reservationMapper.toDTO(reservation);
    }

    @Transactional
    @Override
    public ReservationDTO makeRoundTrip(RoundTripReservationCreate dto) {
        MainDTO mainDTO = dto.main();
        BookingAgent bookingAgent = bookingAgentRepository.loadById(mainDTO.bookingAgentId());

        FlightSchedule flight = flightScheduleRepository.loadById(dto.flightNumber());
        FlightSchedule returnFlight = flightScheduleRepository.loadById(dto.returnFlightNumber());

        RoundTripFlightDTO roundTrip = flightScheduleService.getRoundTripDTO(new RoundTrip(flight, returnFlight))
                .orElseThrow(() -> new BadRequestException("Invalid round trip flight"));
        //check
        checkToAvailability(roundTrip.travelClassAvailableSeats(), roundTrip.travelClassCostList(), mainDTO.travelClassCode());

        Long paymentAmount = roundTrip.travelClassCostList().get(mainDTO.travelClassCode());
        Passenger passenger = passengerService.getPassenger(mainDTO.passenger());

        ItineraryReservation reservation = makeReservation(bookingAgent, passenger, paymentAmount, mainDTO, flight.getFlightNumber(), returnFlight.getFlightNumber());

        return reservationMapper.toDTO(reservation);
    }

    @Override
    public List<FlightScheduleDTO> getFlightList(Long reservationId) {
        List<FlightSchedule> flightList = reservationRepository.getFlightListByReservationId(reservationId);
        return flightScheduleMapper.toDTOList(flightList);
    }

    @Transactional
    @Override
    public ReservationDTO makeFlexible(ReservationFlexibleDTO dto) {
        MainDTO mainDTO = dto.mainDTO();
        Optional<Airport> originAirportOptional = airportRepository.findFirstByCity(dto.departureCity());
        Optional<Airport> destinationAirportOptional = airportRepository.findFirstByCity(dto.arrivalCity());
        if (originAirportOptional.isEmpty() || destinationAirportOptional.isEmpty()) {
            throw new BadRequestException("Departure or arrival city are not found");
        }
        Airport originAirport = originAirportOptional.get();
        Airport destinationAirport = destinationAirportOptional.get();
        // airlineCode,usualAircraftTypeCode,arrivalDateTime
        FlightSchedule flightSchedule = FlightSchedule.builder()
                .departureDateTime(dto.departureTime())
                .originAirport(originAirport)
                .destinationAirport(destinationAirport)
                .build();
        flightScheduleRepository.save(flightSchedule);
        Leg leg = Leg.builder()
                .destinationAirport(destinationAirport.getAirportCode())
                .originAirport(originAirport.getAirportCode())
                .flightSchedule(flightSchedule)
                .build();
        legRepository.save(leg);
        BookingAgent bookingAgent = bookingAgentRepository.loadById(dto.mainDTO().bookingAgentId());
        Passenger passenger = passengerService.getPassenger(dto.mainDTO().passenger());

        ItineraryReservation reservation = makeReservation(bookingAgent, passenger, dto.payment(), mainDTO, flightSchedule.getFlightNumber());


        return reservationMapper.toDTO(reservation);
    }

    @Override
    public void checkToConfirmation(Long reservationId, TravelClassCode travelClassCode) {
        List<FlightSchedule> flightList = itineraryLegRepository.findFlightByReservationId(reservationId);
        if (flightList.isEmpty()) {
            throw new BadRequestException("Reservation flight is invalid");
        }

        Map<TravelClassCode, Integer> availableSeats;
        Map<TravelClassCode, Long> costList;

        if (flightList.size() == 1) {
            OneWayFlightDTO oneWayFlight = flightScheduleService.getOneWayFlight(flightList.getFirst())
                    .orElseThrow(() -> new BadRequestException("Invalid one way flight"));

            availableSeats = oneWayFlight.travelClassAvailableSeats();
            costList = oneWayFlight.travelClassCostList();
        } else {
            RoundTripFlightDTO roundTrip = flightScheduleService.getRoundTripDTO(new RoundTrip(flightList.get(0), flightList.get(1)))
                    .orElseThrow(() -> new BadRequestException("Invalid round trip flight"));

            availableSeats = roundTrip.travelClassAvailableSeats();
            costList = roundTrip.travelClassCostList();
        }

        checkToAvailability(availableSeats, costList, travelClassCode);
    }

    @Override
    public void reverseReservation(Long reservationId) {
        List<FlightSchedule> flightList = itineraryLegRepository.findFlightByReservationId(reservationId);
        if (flightList.isEmpty()) {
            return;
        }

        FlightSchedule flight = flightList.getFirst();
        if (flight.getDepartureDateTime().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new BadRequestException("Reservation can be reverse before 1 hour departure time");
        }
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

        return reservationMapper.toDTO(reservation);
    }

    @Override
    public Page<ReservationDTO> getList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateReservationMade").descending());

        Page<ItineraryReservation> pageObj = reservationRepository.findByPassenger_CustomerId(authUtil.loadLoggedUser().getId(), pageable);
        List<ReservationDTO> dtoList = reservationMapper.toDTOList(pageObj.getContent());

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }

    @Override
    public Page<PassengerDTO> findPassengersWithReservedSeatsOnFlight(String flightNumber, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Passenger> pageObj = reservationRepository.findPassengersWithReservedSeatsOnFlight(flightNumber, pageable);
        List<PassengerDTO> dtoList = passengerMapper.toDTOList(pageObj.getContent());

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }

    private void checkToAvailability(Map<TravelClassCode, Integer> availableSeats, Map<TravelClassCode, Long> travelClassCostList, TravelClassCode travelClassCode) {
        if (availableSeats.getOrDefault(travelClassCode, 0) < 1) {
            throw new BadRequestException("There is no available seat for this one way flight with request travel class code");
        }

        Long paymentAmount = travelClassCostList.get(travelClassCode);
        if (paymentAmount == null) {
            throw new BadRequestException("Invalid round trip flight");
        }
    }

    private ItineraryReservation makeReservation(BookingAgent bookingAgent, Passenger passenger, Long
            paymentAmount, MainDTO mainDTO, Long... flightNumbers) {
        ItineraryReservation reservation = ItineraryReservation.builder()
                .agent(bookingAgent)
                .passenger(passenger)
                .reservationStatusCode(ReservationStatusCode.CREATED)
                .dateReservationMade(LocalDateTime.now())
                .ticketTypeCode(mainDTO.ticketTypeCode())
                .travelClassCode(mainDTO.travelClassCode())
                .numberInParty(randomUtil.getRandomSeatNumber())
                .build();
        reservationRepository.save(reservation);

        itineraryLegService.addItineraryLegs(reservation, flightNumbers);

        User user = authUtil.loadLoggedUser();
        if (mainDTO.useCashback() || user.getCashbackAmount() < 0) {
            paymentAmount = useCashback(paymentAmount);
        }

        addPayment(reservation, paymentAmount);

        return reservation;
    }

    private long useCashback(long paymentAmount) {
        User user = authUtil.loadLoggedUser();
        if (paymentAmount >= user.getCashbackAmount()) {
            paymentAmount = paymentAmount - user.getCashbackAmount();
            user.setCashbackAmount(0L);
        } else {
            paymentAmount = 0L;
            user.setCashbackAmount(user.getCashbackAmount() - paymentAmount);
        }
        userRepository.save(user);

        return paymentAmount;
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