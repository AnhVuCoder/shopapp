package com.ngleanhvu.shopapp.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductListResponse {
    @JsonProperty("product_response_list")
    List<ProductResponse> productResponseList;
    @JsonProperty("total_pages")
    int totalPages;
}
