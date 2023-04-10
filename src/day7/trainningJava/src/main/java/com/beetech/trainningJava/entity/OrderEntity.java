package com.beetech.trainningJava.entity;

import com.beetech.trainningJava.enums.PaymentMethod;
import com.beetech.trainningJava.enums.PaymentStatus;
import com.beetech.trainningJava.model.OrderModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`order`")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "order_date", nullable = false)
    private String orderDate;

    @Column(name = "delivery_date")
    private String deliveryDate;

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity = 0;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "payment_method", nullable = false, length = 6)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "payment_status", nullable = false, length = 7)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    @JsonManagedReference
    private CartEntity cart;

    @OneToMany(mappedBy = "order")
    @JsonBackReference
    private Set<CartProductOrderEntity> cartProductOrderEntities = new LinkedHashSet<>();

    public OrderEntity(OrderModel orderModel) {
        this(orderModel.getId(),
                orderModel.getOrderDate(),
                orderModel.getDeliveryDate(),
                orderModel.getTotalQuantity(),
                orderModel.getTotalAmount(),
                orderModel.getPaymentMethod(),
                orderModel.getPaymentStatus(),
                orderModel.getCart(),
                orderModel.getCartProductOrderEntities());
    }
}