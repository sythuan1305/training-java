package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.CartProductEntity;
import com.beetech.trainningJava.entity.CartProductOrderEntity;
import com.beetech.trainningJava.entity.OrderEntity;
import com.beetech.trainningJava.enums.PaymentStatus;
import com.beetech.trainningJava.repository.CartProductOrderRepository;
import com.beetech.trainningJava.service.ICartProductOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class này dùng để triển khai các phương thức của interface ICartProductOrder
 * 
 * @see ICartProductOrder
 */
@Service
@RequiredArgsConstructor(onConstructor_ = { @Autowired })
public class CartProductOrderImp implements ICartProductOrder {
    private final CartProductOrderRepository cartProductOrderRepository;

    @Override
    public CartProductOrderEntity saveCartProductOrderEntityByEntity(CartProductOrderEntity cartProductOrderEntity) {
        return cartProductOrderRepository.save(cartProductOrderEntity);
    }

    @Override
    public List<CartProductOrderEntity> updateCartProductOrderEntityListAfterBoughtByOrderEntity(
            OrderEntity orderEntity) {
        // Cập nhật trạng thái thanh toán của order thành PAID
        orderEntity.setPaymentStatus(PaymentStatus.PAID);
        // Câp nhật trạng thái đã mua của cart product thành true
        List<CartProductOrderEntity> cartProductOrderEntities = findCartProductOrderEntityListByOrderEntity(
                orderEntity);
        return cartProductOrderEntities.stream().map(cartProductOrderEntity -> {
            cartProductOrderEntity.setOrder(orderEntity);
            CartProductEntity cartProductEntity = cartProductOrderEntity.getCartProduct();
            cartProductEntity.setBought(true);
            return saveCartProductOrderEntityByEntity(cartProductOrderEntity); // TODO SQL
        }).toList();
    }

    @Override
    public List<CartProductOrderEntity> findCartProductOrderEntityListByOrderEntity(OrderEntity orderEntity) {
        return cartProductOrderRepository.findAllByOrderId(orderEntity.getId());
    }

    @Override
    public List<CartProductOrderEntity> saveCartProductOrderEntityListByEntityList(
            List<CartProductOrderEntity> cartProductOrderEntities) {
        return cartProductOrderRepository.saveAll(cartProductOrderEntities);
    }

    @Override
    public List<CartProductOrderEntity> saveCartProductOrderEntityListByCartProductEntityListAndOrderEntity(
            List<CartProductEntity> cartProductEntities, OrderEntity orderEntity) {
        List<CartProductOrderEntity> cartProductOrderEntities = cartProductEntities.stream().map(cartProductEntity -> {
            // Tạo mới cart product order
            CartProductOrderEntity cartProductOrderEntity = new CartProductOrderEntity();
            cartProductOrderEntity.setCartProduct(cartProductEntity);
            cartProductOrderEntity.setOrder(orderEntity);
            return cartProductOrderEntity;
        }).toList();
        return saveCartProductOrderEntityListByEntityList(cartProductOrderEntities);
    }
}
