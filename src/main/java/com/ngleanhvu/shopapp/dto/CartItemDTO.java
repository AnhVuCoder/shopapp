package com.ngleanhvu.shopapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    @JsonProperty("product_id")
    private Integer productId;
    @JsonProperty("quantity")
    private Integer quantity;
}
