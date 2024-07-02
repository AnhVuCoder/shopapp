package com.ngleanhvu.shopapp.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

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
    Date orderDate;
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
}
