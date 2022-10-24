package com.food.ordering.system.order.service.domain.ports.input.message.listener.payment;

import com.food.ordering.system.order.service.domain.dto.message.PaymanteResponse;

public interface PaymentResponseMessageListener {
    void paymentComplete(PaymanteResponse paymanteResponse);
    void paymentCancelled(PaymanteResponse paymanteResponse);
}
