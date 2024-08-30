package com.ngleanhvu.shopapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDTO {
    @JsonProperty("id")
    Integer id;
    @JsonProperty("name")
    @NotEmpty(message = "Category name can not be empty")
    String name;
}
