package com.ngleanhvu.shopapp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDTO {
    Integer id;
    @NotEmpty(message = "Category name can not be empty")
    String name;
}
