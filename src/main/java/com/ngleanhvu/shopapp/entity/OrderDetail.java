package com.ngleanhvu.shopapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonBackReference
    Order order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
    @Column(name = "price", nullable = false)
    float price;
    @Column(name = "number_of_products", nullable = false)
    @JsonProperty("number_of_products")
    int numberOfProducts;
    @Column(name = "total_money", nullable = false)
    @JsonProperty("total_money")
    float totalMoney;
    String color;
}
