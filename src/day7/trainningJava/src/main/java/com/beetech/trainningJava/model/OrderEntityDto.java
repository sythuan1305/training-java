package com.beetech.trainningJava.model;

import com.beetech.trainningJava.entity.OrderEntity;
import com.beetech.trainningJava.enums.PaymentMethod;
import com.beetech.trainningJava.enums.PaymentStatus;
import com.beetech.trainningJava.enums.ShippingMethod;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link OrderEntity}
 */
@Value
public class OrderEntityDto implements Serializable {
    Integer id;
    LocalDateTime orderDate;
    LocalDateTime deliveryDate;
    Integer totalQuantity;
    BigDecimal totalAmount;
    BigDecimal totalPrice;
    BigDecimal totalDiscount;
    PaymentMethod paymentMethod;
    PaymentStatus paymentStatus;
    String address;
    String email;
    ShippingMethod shippingMethod;
    List<CartProductEntityDto> cartProductEntityDto;
}