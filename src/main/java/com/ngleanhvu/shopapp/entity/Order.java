package com.ngleanhvu.shopapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    @Column(name = "fullname", length = 100)
    String fullName;
    @Column(name = "email", length = 100)
    String email;
    @Column(name = "phone_number", length = 100, nullable = false)
    String phoneNumber;
    @Column(name = "address", length = 1550, nullable = false)
    String address;
    @Column(name = "note", length = 100)
    String note;
    @Column(name = "order_date")
    Date orderDate;
    @Column(name = "status")
    String status;
    @Column(name = "total_money")
    float totalMoney;
    String shippingMethod;
    String shippingAddress;
    LocalDate shippingDate;
    String trackingNumber;
    @Column(name = "payment_method")
    String paymentMethod;
    //    @Column(name = "payment_status")
//    String paymentStatus;
//    @Column(name = "payment_date")
//    LocalDate paymentDate; // Khi nguoi dung hanh toan moi cap nhat
    boolean active;
}
