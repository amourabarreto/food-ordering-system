package com.food.ordering.system.order.service.domain.dto.message;

import com.food.ordering.system.domain.valueobject.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PaymanteResponse {
    private String id;
    private String sagaId;
    private String oderId;
    private String paymentId;
    private String customerId;
    private Instant createdAt;
    private PaymentStatus paymentStatus;
    private List<String> failureMessages;
}
