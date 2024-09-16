package uz.ccrew.flightmanagement.dto.reservationpayment;

import lombok.Builder;
import uz.ccrew.flightmanagement.enums.PaymentStatusCode;

import java.time.LocalDateTime;

@Builder
public record PaymentDTO(String paymentId,
                         PaymentStatusCode paymentStatusCode,
                         LocalDateTime paymentDate,
                         Long paymentAmount) {
}
