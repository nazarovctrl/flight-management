package uz.ccrew.flightmanagement.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "booking_agents")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingAgent extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer agentId;
    @Column(nullable = false, unique = true)
    private String agentName;
    @Column
    private String agentDetails;
}
