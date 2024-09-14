package uz.ccrew.flightmanagement.dto.passenger;

import lombok.Builder;

@Builder
public record PassengerDTO(Long passengerId,
                           String firstName,
                           String secondName,
                           String lastName,
                           String phoneNumber,
                           String emailAddress,
                           String addressLines,
                           String city,
                           String stateProvinceCountry,
                           String country,
                           String otherPassengerDetails) {
}
