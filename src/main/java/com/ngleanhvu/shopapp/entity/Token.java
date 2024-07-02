package com.ngleanhvu.shopapp.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@Table(name = "tokens")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "token", length = 255)
    String token;
    @Column(name = "token_type", length = 50)
    String tokenType;
    @Column(name = "expiration_date")
    LocalDateTime expirationDate;
    boolean revoked;
    boolean expired;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
