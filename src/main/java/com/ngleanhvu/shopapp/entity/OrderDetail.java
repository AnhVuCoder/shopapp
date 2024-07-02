package com.ngleanhvu.shopapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Table(name = "order_details")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
    @Column(name = "price", nullable = false)
    float price;
    @Column(name = "number_of_products", nullable = false)
    int numberOfProducts;
    @Column(name = "total_money", nullable = false)
    float totalMoney;
    String color;
}
