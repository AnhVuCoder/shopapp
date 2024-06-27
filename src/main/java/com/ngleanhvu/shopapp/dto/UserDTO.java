package com.ngleanhvu.shopapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    Integer id;
    @JsonProperty("fullname")
    String fullName;
    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    String phone_number;
    String address;
    @NotBlank(message = "Password is required")
    String password;
    @JsonProperty("retype_password")
    String retypePassword;
    @JsonProperty("date_of_birth")
    LocalDate dateOfBirth;
    @JsonProperty("facebook_account_id")
    String facebookAccountId;
    @JsonProperty("google_account_id")
    String googleAccountId;
    @NotNull(message = "Role id is required")
    @JsonProperty("role_id")
    Long roleId;
}
