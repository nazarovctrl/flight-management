package uz.ccrew.flightmanagement.entity;

import lombok.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "itinerary_legs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryLeg extends Auditable {
    @EmbeddedId
    private ItineraryLegId id;

    @ManyToOne
    @MapsId("reservationId")
    @JoinColumn(name = "reservation_id", foreignKey = @ForeignKey(name = "itinerary_legs_f1"), nullable = false)
    private ItineraryReservation reservation;

    @OneToOne
    @MapsId("legId")
    @JoinColumn(name = "leg_id", foreignKey = @ForeignKey(name = "itinerary_legs_f2"), nullable = false)
    private Leg leg;


    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItineraryLegId implements Serializable {
        private Long reservationId;
        private Long legId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ItineraryLegId that = (ItineraryLegId) o;
            return Objects.equals(reservationId, that.reservationId) && Objects.equals(legId, that.legId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(reservationId, legId);
        }
    }
}
