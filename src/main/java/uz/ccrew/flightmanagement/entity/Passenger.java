package uz.ccrew.flightmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "passengers")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passengerId;
    @Column
    private String firstName;
    @Column
    private String secondName;
    @Column
    private String lastName;
    @Column
    private String phoneNumber;
    @Column
    private String emailAddress;
    @Column
    private String addressLines;
    @Column
    private String city;
    @Column
    private String stateProvinceCountry;
    @Column
    private String country;
    @Column
    private String otherPassengerDetails;
}
