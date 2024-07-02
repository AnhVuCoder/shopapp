package com.ngleanhvu.shopapp.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ngleanhvu.shopapp.entity.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse extends BaseResponse {
    Integer id;
    String name;
    float price;
    String thumbnail;
    String description;
    @JsonProperty("category_id")
    Integer categoryId;

    public static ProductResponse fromProductResponse(Product product) {
        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .build();
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;
    }
}
