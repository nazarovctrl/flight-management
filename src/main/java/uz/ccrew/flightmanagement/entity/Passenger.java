package uz.ccrew.flightmanagement.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "passengers", uniqueConstraints = {
        @UniqueConstraint(name = "passengers_u1", columnNames = {"firstName", "secondName", "lastName","phoneNumber"})})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passengerId;
    @Column(nullable = false)
    private String firstName;
    @Column
    private String secondName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String phoneNumber;
    @Column
    private String emailAddress;
    @Column(nullable = false)
    private String addressLines;
    @Column(nullable = false)
    private String city;
    @Column
    private String stateProvinceCountry;
    @Column(nullable = false)
    private String country;
    @Column
    private String otherPassengerDetails;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    @OneToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "passengers_f1"), nullable = false, insertable = false, updatable = false)
    private User customer;
}
