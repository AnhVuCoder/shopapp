package com.ngleanhvu.shopapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDTO {
    Integer id;
    @Min(value = 1, message = "User's id must be > 0")
    @JsonProperty("user_id")
    Integer userId;
    @JsonProperty("fullname")
    String fullName;
    String email;
    String address;
    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    String phoneNumber;
    String note;
    @Min(value = 0, message = "Total money must be >= 0")
    @JsonProperty("total_money")
    float totalMoney;
    @JsonProperty("shipping_address")
    String shippingAddress;
    @JsonProperty("shipping_method")
    String shippingMethod;
    @JsonProperty("payment_method")
    String paymentMethod;
    @JsonProperty("shipping_date")
    LocalDate shippingDate;
    @JsonProperty("cart_items")
    List<CartItemDTO> cartItemDTOs;
    @JsonProperty("status")
    String status;
}
