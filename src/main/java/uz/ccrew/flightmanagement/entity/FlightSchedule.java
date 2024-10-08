package uz.ccrew.flightmanagement.entity;

import uz.ccrew.flightmanagement.enums.AirlineCode;
import uz.ccrew.flightmanagement.enums.AircraftTypeCode;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "flight_schedules")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightSchedule extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightNumber;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AirlineCode airlineCode;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AircraftTypeCode usualAircraftTypeCode;
    @ManyToOne
    @JoinColumn(name = "origin_airport_code", foreignKey = @ForeignKey(name = "flight_schedules_f1"), nullable = false)
    private Airport originAirport;
    @ManyToOne
    @JoinColumn(name = "destination_airport_code", foreignKey = @ForeignKey(name = "flight_schedules_f2"), nullable = false)
    private Airport destinationAirport;
    @Column(nullable = false)
    private LocalDateTime departureDateTime;
    @Column(nullable = false)
    private LocalDateTime arrivalDateTime;
}
