package com.ngleanhvu.shopapp.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@Table(name = "social_accounts")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(nullable = false, length = 200)
    String provider;
    @Column(nullable = false, length = 200, name = "provider_id")
    String providerId;
    @Column(name = "name", length = 150)
    String name;
    @Column(name = "email", length = 150)
    String email;
}
