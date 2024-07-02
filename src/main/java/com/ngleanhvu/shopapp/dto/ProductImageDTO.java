package com.ngleanhvu.shopapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductImageDTO {
    Integer id;
    @Min(value = 1)
    @JsonProperty("product_id")
    private Integer productId;
    @JsonProperty("image_url")
    @Size(min = 5, max = 200)
    private String imageUrl;
}
