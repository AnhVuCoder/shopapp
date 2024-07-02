package com.ngleanhvu.shopapp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDTO {
    Integer id;
    @NotEmpty(message = "Category name can not be empty")
    String name;
}
