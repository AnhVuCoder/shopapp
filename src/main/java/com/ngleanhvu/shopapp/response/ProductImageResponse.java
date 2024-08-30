package com.ngleanhvu.shopapp.response;

import com.ngleanhvu.shopapp.entity.ProductImage;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageResponse {
    private Integer id;
    private String image_url;
    public static ProductImageResponse getProductImageResponse(ProductImage productImage){
        return ProductImageResponse.builder()
                .id(productImage.getId())
                .image_url(productImage.getImageUrl())
                .build();
    }
}
