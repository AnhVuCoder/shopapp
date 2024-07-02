package com.ngleanhvu.shopapp.entity;

public enum OrderStatus {
    PENDING("PENDING"),
    PROCESSING("PROCESSING"),
    SHIPPED("SHIPPED"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED");
    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }
}