package com.ngleanhvu.shopapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDTO extends BaseEntity {
    Integer id;
    @NotBlank(message = "Product name must not be empty")
    @Size(min=3, max=200, message = "Name must be between 3 and 200 characters")
    String name;
    @Min(value = 0, message = "Price must be greater than 0 or equal 0")
    @Max(value = 10000000, message = "Price must be less than 10000000 or equal 10000000")
    float price;
    String thumbnail;
    @JsonProperty("category_id")
    String categoryId;
    List<MultipartFile> files;
}
