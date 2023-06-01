package com.beetech.trainningJava.entity;

import com.beetech.trainningJava.enums.PaymentMethod;
import com.beetech.trainningJava.enums.PaymentStatus;
import com.beetech.trainningJava.enums.ShippingMethod;
import com.beetech.trainningJava.model.DiscountEntityDto;
import com.beetech.trainningJava.model.OrderModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Entity này dùng để lưu thông tin đơn hàng
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`order`")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @CreationTimestamp
    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity = 0;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2, columnDefinition = "decimal(10,2) default '0.00'")
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @Column(name = "total_discount", nullable = false, precision = 10, scale = 2, columnDefinition = "decimal(10,2) default '0.00'")
    private BigDecimal totalDiscount = BigDecimal.ZERO;

    @Column(name = "payment_method", nullable = false, length = 6)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "payment_status", nullable = false, length = 7)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "shipping_method", nullable = false, length = 7)
    @Enumerated(EnumType.STRING)
    private ShippingMethod shippingMethod;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JsonBackReference
    private Set<CartProductOrderEntity> cartProductOrderEntities = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private CartEntity cart;

    public OrderEntity(OrderModel orderModel) {
        this(orderModel.getId(),
                orderModel.getOrderDate(),
                orderModel.getDeliveryDate(),
                orderModel.getTotalQuantity(),
                orderModel.getTotalAmount(),
                orderModel.getTotalPrice(),
                orderModel.getTotalDiscount(),
                orderModel.getPaymentMethod(),
                orderModel.getPaymentStatus(),
                orderModel.getShippingMethod(),
                orderModel.getAddress(),
                orderModel.getEmail(),
                orderModel.getCartProductOrderEntities(),
                orderModel.getCart());
    }

    public OrderEntity(List<CartProductEntity> cartProductEntities,
                       DiscountEntityDto discountEntityDto,
                       PaymentMethod paymentMethod,
                       ShippingMethod shippingMethod,
                       String address,
                       String email) {
        this.id = null;
        this.orderDate = LocalDateTime.now();
        this.deliveryDate = null;
        this.totalQuantity = cartProductEntities.stream().mapToInt(CartProductEntity::getQuantity).sum();
        this.totalPrice = cartProductEntities.stream().map(CartProductEntity::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (discountEntityDto != null) {
            this.totalDiscount = switch (discountEntityDto.getDiscountType()) {
                case PERCENT -> this.totalPrice
                        .multiply(discountEntityDto.getDiscountValue())
                        .divide(BigDecimal.valueOf(100));
                case MONEY -> discountEntityDto.getDiscountValue();
            };
        }
        this.totalAmount = this.totalPrice.subtract(this.totalDiscount);
        this.paymentMethod = paymentMethod;
        this.paymentStatus = PaymentStatus.PENDING;
        this.cartProductOrderEntities = cartProductEntities.stream().map(
                cartProductEntity -> new CartProductOrderEntity(null, this, cartProductEntity)
        ).collect(Collectors.toSet());
        if (cartProductEntities.size() > 0)
            this.cart = cartProductEntities.get(0).getCart();
        this.shippingMethod = shippingMethod;
        this.address = address;
        this.email = email;
    }
}