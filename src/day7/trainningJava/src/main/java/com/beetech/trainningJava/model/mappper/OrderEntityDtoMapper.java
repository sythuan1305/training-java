package com.beetech.trainningJava.model.mappper;

import com.beetech.trainningJava.entity.OrderEntity;
import com.beetech.trainningJava.model.OrderEntityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderEntityDtoMapper implements Function<OrderEntity, OrderEntityDto> {
    private final CartProductEntityDtoMapper cartProductEntityDtoMapper;

    @Override
    public OrderEntityDto apply(OrderEntity orderEntity) {
        return new OrderEntityDto(
                orderEntity.getId(),
                orderEntity.getOrderDate(),
                orderEntity.getDeliveryDate(),
                orderEntity.getTotalQuantity(),
                orderEntity.getTotalAmount(),
                orderEntity.getTotalPrice(),
                orderEntity.getTotalDiscount(),
                orderEntity.getPaymentMethod(),
                orderEntity.getPaymentStatus(),
                orderEntity.getAddress(),
                orderEntity.getEmail(),
                orderEntity.getShippingMethod(),
                orderEntity.getCartProductOrderEntities()
                        .stream()
                        .map(cartProductOrderEntity -> cartProductEntityDtoMapper.applyWithoutImageUrlList(cartProductOrderEntity.getCartProduct()))
                        .toList());
    }

    public OrderEntityDto applyWithNoCartProduct(OrderEntity orderEntity) {
        return new OrderEntityDto(
                orderEntity.getId(),
                orderEntity.getOrderDate(),
                orderEntity.getDeliveryDate(),
                orderEntity.getTotalQuantity(),
                orderEntity.getTotalAmount(),
                orderEntity.getTotalPrice(),
                orderEntity.getTotalDiscount(),
                orderEntity.getPaymentMethod(),
                orderEntity.getPaymentStatus(),
                orderEntity.getAddress(),
                orderEntity.getEmail(),
                orderEntity.getShippingMethod(),
                null);
    }
}
