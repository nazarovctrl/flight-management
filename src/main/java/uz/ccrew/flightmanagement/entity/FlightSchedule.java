package uz.ccrew.flightmanagement.entity;

import uz.ccrew.flightmanagement.enums.AircraftTypeCode;
import uz.ccrew.flightmanagement.enums.AirlineCode;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "flight_schedules")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class FlightSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightNumber;
    @Enumerated(EnumType.STRING)
    @Column
    private AirlineCode airlineCode;
    @Enumerated(EnumType.STRING)
    @Column
    private AircraftTypeCode usualAircraftTypeCode;
    @ManyToOne
    @JoinColumn(name = "origin_airport_code", foreignKey = @ForeignKey(name = "flight_schedules_f1"), nullable = false)
    private Airport originAirport;
    @ManyToOne
    @JoinColumn(name = "destination_airport_code", foreignKey = @ForeignKey(name = "flight_schedules_f2"), nullable = false)
    private Airport destinationAirport;
    @Column
    private LocalDateTime departureDateTime;
    @Column(nullable = true)
    private LocalDateTime arrivalDateTime;
}
