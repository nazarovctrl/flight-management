package uz.ccrew.flightmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
import java.util.Objects;
import java.io.Serializable;

@Entity
@Table(name = "reservation_payments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationPayment {
    @EmbeddedId
    private ReservationPaymentId id;

    @ManyToOne
    @MapsId("reservationId")
    @JoinColumn(name = "reservation_id", foreignKey = @ForeignKey(name = "reservation_payments_f1"), nullable = false)
    private ItineraryReservation reservation;

    @OneToOne
    @MapsId("paymentId")
    @JoinColumn(name = "payment_id", foreignKey = @ForeignKey(name = "reservation_payments_f2"), nullable = false)
    private Payment payment;


    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReservationPaymentId implements Serializable {
        private Long reservationId;
        private UUID paymentId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ReservationPaymentId that = (ReservationPaymentId) o;
            return Objects.equals(reservationId, that.reservationId) && Objects.equals(paymentId, that.paymentId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(reservationId, paymentId);
        }
    }
}
