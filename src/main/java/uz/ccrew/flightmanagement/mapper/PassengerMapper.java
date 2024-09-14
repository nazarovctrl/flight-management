package uz.ccrew.flightmanagement.mapper;

import uz.ccrew.flightmanagement.entity.Passenger;
import uz.ccrew.flightmanagement.dto.passenger.PassengerDTO;
import uz.ccrew.flightmanagement.dto.passenger.PassengerCreateDTO;

import org.springframework.stereotype.Component;

@Component
public class PassengerMapper implements Mapper<PassengerCreateDTO, PassengerDTO, Passenger> {
    @Override
    public Passenger toEntity(PassengerCreateDTO dto) {
        return Passenger.builder()
                .firstName(dto.firstName())
                .secondName(dto.secondName())
                .lastName(dto.lastName())
                .phoneNumber(dto.phoneNumber())
                .emailAddress(dto.emailAddress())
                .addressLines(dto.addressLines())
                .city(dto.city())
                .stateProvinceCountry(dto.stateProvinceCountry())
                .country(dto.country())
                .otherPassengerDetails(dto.otherPassengerDetails())
                .build();
    }

    @Override
    public PassengerDTO toDTO(Passenger passenger) {
        return PassengerDTO.builder()
                .passengerId(passenger.getPassengerId())
                .firstName(passenger.getFirstName())
                .secondName(passenger.getSecondName())
                .lastName(passenger.getLastName())
                .phoneNumber(passenger.getPhoneNumber())
                .emailAddress(passenger.getEmailAddress())
                .addressLines(passenger.getAddressLines())
                .city(passenger.getCity())
                .stateProvinceCountry(passenger.getStateProvinceCountry())
                .country(passenger.getCountry())
                .otherPassengerDetails(passenger.getOtherPassengerDetails())
                .build();
    }
}
