package uz.ccrew.flightmanagement.dto.passenger;

import lombok.Builder;
import jakarta.validation.constraints.NotBlank;

@Builder
public record PassengerCreateDTO(@NotBlank(message = "Invalid FirstName")
                                 String firstName,
                                 String secondName,
                                 @NotBlank(message = "Invalid lastName")
                                 String lastName,
                                 @NotBlank(message = "Invalid phoneNumber")
                                 String phoneNumber,
                                 String emailAddress,
                                 @NotBlank(message = "Invalid address line")
                                 String addressLines,
                                 @NotBlank(message = "Invalid City")
                                 String city,
                                 String stateProvinceCountry,
                                 @NotBlank(message = "Invalid Country")
                                 String country,
                                 String otherPassengerDetails) {
}
