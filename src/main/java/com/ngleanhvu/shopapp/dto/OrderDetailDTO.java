package com.ngleanhvu.shopapp.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailDTO {
    Integer id;
    @Min(value = 1, message = "Order id must be >= 1")
    @JsonProperty("order_id")
    Integer orderId;
    @Min(value = 1, message = "Product id must be >= 1")
    @JsonProperty("product_id")
    Integer productId;
    @Min(value = 0, message = "Price must be >= 0")
    float price;
    @Min(value = 1, message = "Number of product must be >= 1")
    @JsonProperty("total_money")
    float totalMoney;
    @JsonProperty("number_of_products")
    int numberOfProducts;
    String color;
}
