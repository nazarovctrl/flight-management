package uz.ccrew.flightmanagement.entity;

import uz.ccrew.flightmanagement.enums.TicketTypeCode;
import uz.ccrew.flightmanagement.enums.TravelClassCode;
import uz.ccrew.flightmanagement.enums.ReservationStatusCode;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "itinerary_reservations")
public class ItineraryReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;
    @ManyToOne
    @JoinColumn(name = "agent_id", foreignKey = @ForeignKey(name = "itinerary_reservations_f1"), nullable = false)
    private BookingAgent agent;
    @ManyToOne
    @JoinColumn(name = "passenger_id", foreignKey = @ForeignKey(name = "itinerary_reservations_f2"), nullable = false)
    private Passenger passenger;
    @Enumerated(EnumType.STRING)
    @Column
    private ReservationStatusCode reservationStatusCode;
    @Enumerated(EnumType.STRING)
    @Column
    private TicketTypeCode ticketTypeCode;
    @Enumerated(EnumType.STRING)
    @Column
    private TravelClassCode travelClassCode;
    @Column
    private LocalDateTime dateReservationMade;
    @Column
    private Integer numberInParty;
}
