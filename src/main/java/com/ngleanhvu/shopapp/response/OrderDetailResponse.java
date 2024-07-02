package com.ngleanhvu.shopapp.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    Integer id;
    @JsonProperty("order_id")
    Integer orderId;
    @JsonProperty("product_id")
    Integer productId;
    @Min(value = 0, message = "Price must be >= 0")
    float price;
    @JsonProperty("total_money")
    float totalMoney;
    @JsonProperty("number_of_products")
    int numberOfProducts;
    String color;
}
