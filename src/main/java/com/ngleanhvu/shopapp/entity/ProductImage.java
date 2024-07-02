package com.ngleanhvu.shopapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Table(name = "product_images")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
    @Column(name = "image_url", length = 255)
    String imageUrl;
}
