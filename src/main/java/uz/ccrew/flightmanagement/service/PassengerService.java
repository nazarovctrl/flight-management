package uz.ccrew.flightmanagement.service;

import uz.ccrew.flightmanagement.dto.passenger.PassengerDTO;
import uz.ccrew.flightmanagement.dto.passenger.PassengerCreateDTO;
import uz.ccrew.flightmanagement.entity.Passenger;

public interface PassengerService {
    PassengerDTO add(PassengerCreateDTO dto);

    Passenger getPassenger(PassengerCreateDTO passengerCreateDTO);
}
