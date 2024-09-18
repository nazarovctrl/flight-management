package uz.ccrew.flightmanagement.entity;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "legs", uniqueConstraints = {
        @UniqueConstraint(name = "legs_u1", columnNames = {"flight_number", "originAirport", "destinationAirport"})})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Leg extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long legId;
    @ManyToOne
    @JoinColumn(name = "flight_number", foreignKey = @ForeignKey(name = "legs_f1"), nullable = false)
    private FlightSchedule flightSchedule;
    @Column(nullable = false)
    private String originAirport;
    @Column(nullable = false)
    private String destinationAirport;
    @Column
    private LocalDateTime actualDepartureTime;
    @Column
    private LocalDateTime actualArrivalTime;
}
