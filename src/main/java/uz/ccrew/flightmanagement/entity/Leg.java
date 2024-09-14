package uz.ccrew.flightmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "legs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Leg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long legId;
    @ManyToOne
    @JoinColumn(name = "flight_number", foreignKey = @ForeignKey(name = "legs_f1"), nullable = false)
    private FlightSchedule flightSchedule;
    @Column
    private String originAirport;
    @Column
    private String destinationAirport;
    @Column
    private LocalDateTime actualDepartureTime;
    @Column
    private LocalDateTime actualArrivalTime;
}
