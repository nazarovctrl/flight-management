package uz.ccrew.flightmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "booking_agents")
public class BookingAgent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer agentId;
    @Column
    private String agentName;
    @Column
    private String agentDetails;
}
