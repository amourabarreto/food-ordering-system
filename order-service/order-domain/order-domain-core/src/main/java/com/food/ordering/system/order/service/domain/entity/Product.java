package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.BaseEntity;
import com.food.ordering.system.domain.valueobject.ProductId;

import java.util.UUID;

public class Product extends BaseEntity<ProductId> {
    private String name;
    private String price;

    public Product(ProductId productId,String name, String price) {
        super.setId(productId);
        this.name = name;
        this.price = price;
    }

    public Product(ProductId productId) {
        super.setId(productId);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void updateWithConfirmedNameAndPrice(String name, String price) {
        this.name = name;
        this.price = price;
    }
}
