package com.ngleanhvu.shopapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedUserDTO {
    @JsonProperty("phone_number")
    String phoneNumber;
    @JsonProperty("fullname")
    String fullName;
    String address;
    String password;
    @JsonProperty("retype_password")
    String retypePassword;
    @JsonProperty("date_of_birth")
    LocalDate dateOfBirth;
}
