package com.example.productshop.services.serviceModels;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderServiceModel {
    private LocalDateTime orderTime;
    private Integer quantity;
    private BigDecimal price;
    private UserServiceModel user;
    private ProductServiceModel product;

    public OrderServiceModel() {
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public ProductServiceModel getProduct() {
        return product;
    }

    public void setProduct(ProductServiceModel product) {
        this.product = product;
    }
}
