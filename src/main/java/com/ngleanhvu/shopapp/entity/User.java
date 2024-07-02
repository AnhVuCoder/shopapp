package com.ngleanhvu.shopapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Builder
@Table(name = "users")
@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(nullable = false, length = 100, name = "fullname")
    String fullName;
    @Column(nullable = false, name = "phone_number", length = 10)
    String phoneNumber;
    @Column(length = 200)
    String address;
    @Column(length = 200, nullable = false)
    String password;
    @Column(name = "is_active")
    boolean active = true;
    @Column(name = "date_of_birth")
    LocalDate dateOfBirth;
    @Column(name = "facebook_account_id")
    String facebookAccountId;
    @Column(name = "google_account_id")
    String googleAccountId;
    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;
}
