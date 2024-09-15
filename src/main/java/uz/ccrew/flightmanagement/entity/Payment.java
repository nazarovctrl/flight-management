package uz.ccrew.flightmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import uz.ccrew.flightmanagement.enums.PaymentStatusCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @UuidGenerator
    private UUID paymentId;
    @Enumerated(EnumType.STRING)
    @Column
    private PaymentStatusCode paymentStatusCode;
    @Column
    private LocalDateTime paymentDate;
    @Column
    private Long paymentAmount;
}
