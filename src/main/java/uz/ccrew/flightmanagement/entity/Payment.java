package uz.ccrew.flightmanagement.entity;

import uz.ccrew.flightmanagement.enums.PaymentStatusCode;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends Auditable {
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
