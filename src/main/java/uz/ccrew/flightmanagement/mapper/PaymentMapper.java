package uz.ccrew.flightmanagement.mapper;

import org.springframework.stereotype.Component;
import uz.ccrew.flightmanagement.dto.reservationpayment.PaymentDTO;
import uz.ccrew.flightmanagement.entity.Payment;

@Component
public class PaymentMapper implements Mapper<Object, PaymentDTO, Payment>{
    @Override
    public Payment toEntity(Object o) {
        return null;
    }

    @Override
    public PaymentDTO toDTO(Payment payment) {
        return PaymentDTO.builder()
                .paymentId(payment.getPaymentId().toString())
                .paymentStatusCode(payment.getPaymentStatusCode())
                .paymentDate(payment.getPaymentDate())
                .paymentAmount(payment.getPaymentAmount())
                .build();
    }
}
