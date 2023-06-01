package com.beetech.trainningJava.model;

import com.beetech.trainningJava.entity.CartEntity;
import com.beetech.trainningJava.entity.OrderEntity;
import com.beetech.trainningJava.enums.PaymentMethod;
import com.beetech.trainningJava.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Class này bao gôm thông tin của đơn hàng và danh sách sản phẩm, giảm giá của đơn hàng đó
 * @extends OrderEntity
 * @see OrderEntity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel extends OrderEntity {
    public static final double PERCENT = 1d / 100.0d;
    private BigDecimal totalPrice = BigDecimal.ZERO;

    private BigDecimal totalDiscount = BigDecimal.ZERO;

    private DiscountModel discountModel;

    private List<CartProductInforModel> cartProductInforModelList;

    public OrderModel(List<CartProductInforModel> cartProductInforModelList, DiscountModel discountModel,
                      CartEntity cartEntity, PaymentMethod paymentMethod, PaymentStatus paymentStatus) {
        this.discountModel = discountModel;
        cartProductInforModelList.forEach(cartProductInforModel -> {
            super.setTotalQuantity(super.getTotalQuantity() + cartProductInforModel.getQuantity());
            this.totalPrice = this.totalPrice.add(cartProductInforModel.getPrice());
        });
        if (discountModel != null) {
            this.totalDiscount = switch (discountModel.getDiscountType()) {
                case PERCENT -> this.totalPrice.multiply(
                        discountModel.getDiscountValue().multiply(BigDecimal.valueOf(PERCENT)));
                case MONEY -> this.totalPrice.subtract(discountModel.getDiscountValue());
//                default -> this.totalDiscount;
            };
        }
        this.setTotalAmount(this.totalPrice.subtract(this.totalDiscount));
        this.cartProductInforModelList = cartProductInforModelList;

        super.setPaymentMethod(paymentMethod);
        super.setPaymentStatus(paymentStatus);
    }

}

