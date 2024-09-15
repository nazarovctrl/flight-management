package uz.ccrew.flightmanagement.mapper;

import uz.ccrew.flightmanagement.entity.Payment;
import uz.ccrew.flightmanagement.dto.reservationpayment.PaymentDTO;

import org.springframework.stereotype.Component;

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
