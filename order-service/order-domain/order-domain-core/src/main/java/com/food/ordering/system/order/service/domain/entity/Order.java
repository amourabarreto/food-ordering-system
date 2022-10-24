package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueobject.*;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;

import java.util.List;
import java.util.UUID;

public class Order extends AggregateRoot<OrderId> {
    private final CustomerId customerId;
    private final RestaurantId restauranteId;
    private final StreetAddress streetAddress;
    private final Money price;
    private final List<OrderItem> items;

    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;

    private Order(Builder builder) {
        super.setId(builder.orderId);
        customerId = builder.customerId;
        restauranteId = builder.restauranteId;
        streetAddress = builder.deliveryAddress;
        price = builder.price;
        items = builder.items;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failureMessages = builder.failureMessages;
    }

    public static Builder builder() {
        return new Builder();
    }


    public void initializeOrder(){
        setId(new OrderId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        initializerOrderItems();
    }

    private void initializerOrderItems() {
        long  itemId = 1L;
        for(OrderItem orderItem : items){
            orderItem.initializeOrderItem(super.getId(),new OrderItemId(itemId++));
        }

    }

    public void validateOrder(){
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }

    public void pay(){
        if(orderStatus != OrderStatus.PENDING){
            throw new OrderDomainException("Order is not correct state for pay operation!");
        }
        orderStatus = OrderStatus.PAID;
    }

    public void approved(){
        if(orderStatus != OrderStatus.PAID){
            throw new OrderDomainException("Order is not correct state for approved operation!");
        }
        orderStatus = OrderStatus.APROVED;
    }

    public void initCancel(List<String> failureMessages){
        if(orderStatus != OrderStatus.PAID){
            throw new OrderDomainException("Order is not correct state for initCancel operation!");
        }
        orderStatus = OrderStatus.CANCELING;
        updatefailureMessages(failureMessages);
    }

    private void updatefailureMessages(List<String> failureMessages) {
        if(this.failureMessages != null && failureMessages !=null){
            this.failureMessages.addAll(failureMessages.stream().filter(message -> !message.isEmpty()).toList());
        }
        if(this.failureMessages == null){
            this.failureMessages = failureMessages;
        }
    }

    public void cancel(List<String> failureMessages){
        if(!(orderStatus == OrderStatus.CANCELING || orderStatus == OrderStatus.PENDING)){
            throw new OrderDomainException("Order is not correct state for cacel operation!");
        }
        orderStatus = OrderStatus.CANCELLED;
        updatefailureMessages(failureMessages);
    }

    private void validateItemsPrice() {
       Money orderItemsTotal = items.stream().map(orderItem -> {
           validateItemPrice(orderItem);
           return orderItem.getSubTotal();
        }).reduce(Money.ZERO,Money::add);

       if(price.equals(orderItemsTotal) == false){
           throw new OrderDomainException("Total price: "+ price.getAmmount()
                   + " is not equal to Order items total: "+orderItemsTotal.getAmmount()+"!");
       }

    }

    private void validateItemPrice(OrderItem orderItem) {
        if(orderItem.isValidPrice()==false){
            throw new OrderDomainException("Order item price: "+orderItem.getPrice().getAmmount()
                    +" is note valid for product "+ orderItem.getProduct().getId().getValue() );
        }

    }

    private void validateTotalPrice() {
        if (price == null || price.isGreaterThanZero() == false){
            throw new OrderDomainException("Total price must be greater than zero!");
        }
    }

    private void validateInitialOrder() {
        if( orderStatus != null || getId() != null ){
            throw new OrderDomainException("Order is not in correct state for initializaton!");
        }
    }




    public CustomerId getCustomerId() {
        return customerId;
    }

    public RestaurantId getRestauranteId() {
        return restauranteId;
    }

    public StreetAddress getStreetAddress() {
        return streetAddress;
    }

    public Money getPrice() {
        return price;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }


    public static final class Builder {
        private OrderId orderId;
        private CustomerId customerId;
        private RestaurantId restauranteId;
        private StreetAddress deliveryAddress;
        private Money price;
        private List<OrderItem> items;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failureMessages;

        private Builder() {
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder restauranteId(RestaurantId val) {
            restauranteId = val;
            return this;
        }

        public Builder deliveryAddress(StreetAddress val) {
            deliveryAddress = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder items(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
