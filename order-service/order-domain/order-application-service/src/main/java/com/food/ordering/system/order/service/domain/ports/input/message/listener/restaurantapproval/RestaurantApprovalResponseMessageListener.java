package com.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval;

import com.food.ordering.system.order.service.domain.dto.message.RestauranteApprovalResponse;

public interface RestaurantApprovalResponseMessageListener {

    void orderApproved(RestauranteApprovalResponse restauranteApprovalResponse);
    void orderRejected(RestauranteApprovalResponse restauranteApprovalResponse);
}
