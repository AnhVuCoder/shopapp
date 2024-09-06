package com.ngleanhvu.shopapp.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ngleanhvu.shopapp.entity.Order;
import com.ngleanhvu.shopapp.entity.OrderDetail;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Integer id;
    @JsonProperty("user_id")
    Integer userId;
    @JsonProperty("fullname")
    String fullName;
    @JsonProperty("phone_number")
    String phoneNumber;
    String address;
    String note;
    @JsonProperty("order_date")
    LocalDate orderDate;
    @JsonProperty("status")
    String status;
    @JsonProperty("total_money")
    Float totalMoney;
    @JsonProperty("shipping_method")
    String shippingMethod;
    @JsonProperty("shipping_address")
    String shippingAddress;
    @JsonProperty("shipping_date")
    LocalDate shippingDate;
    @JsonProperty("tracking_number")
    String trackingNumber;
    @JsonProperty("payment_method")
    String paymentMethod;
    @JsonProperty("active")
    boolean active;
    @JsonProperty("order_details")
    List<OrderDetail> orderDetails;

    public static OrderResponse fromOrder(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .fullName(order.getFullName())
                .phoneNumber(order.getPhoneNumber())
                .address(order.getAddress())
                .note(order.getNote())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .totalMoney(order.getTotalMoney())
                .shippingMethod(order.getShippingMethod())
                .shippingAddress(order.getShippingAddress())
                .shippingDate(order.getShippingDate())
                .active(order.isActive())
                .orderDetails(order.getOrderDetails())
                .build();
    }
}
