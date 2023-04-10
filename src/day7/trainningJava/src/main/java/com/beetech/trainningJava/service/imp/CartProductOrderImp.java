package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.CartProductEntity;
import com.beetech.trainningJava.entity.CartProductOrderEntity;
import com.beetech.trainningJava.entity.OrderEntity;
import com.beetech.trainningJava.enums.PaymentStatus;
import com.beetech.trainningJava.repository.CartProductOrderRepository;
import com.beetech.trainningJava.service.ICartProductOrder;
import com.beetech.trainningJava.service.ICartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartProductOrderImp implements ICartProductOrder {
    @Autowired
    private CartProductOrderRepository cartProductOrderRepository;

    @Autowired
    private ICartProductService cartProductService;

    @Override
    public CartProductOrderEntity save(CartProductOrderEntity cartProductOrderEntity) {
        return cartProductOrderRepository.save(cartProductOrderEntity);
    }

    @Override
    public List<CartProductOrderEntity> updateCartProductOrderAfterBought(OrderEntity orderEntity) {
        orderEntity.setPaymentStatus(PaymentStatus.PAID);
        List<CartProductOrderEntity> cartProductOrderEntities = findAllByOrder(orderEntity);
        return cartProductOrderEntities.stream().map(cartProductOrderEntity -> {
            cartProductOrderEntity.setOrder(orderEntity);
            CartProductEntity cartProductEntity = cartProductOrderEntity.getCartProduct();
            cartProductEntity.setBought(true);
            return save(cartProductOrderEntity);
        }).toList();
    }

    @Override
    public List<CartProductOrderEntity> findAllByOrder(OrderEntity orderEntity) {
        return cartProductOrderRepository.findAllByOrderId(orderEntity.getId());
    }

    @Override
    public List<CartProductOrderEntity> saveAll(List<CartProductOrderEntity> cartProductOrderEntities) {
        return cartProductOrderRepository.saveAll(cartProductOrderEntities);
    }

    @Override
    public List<CartProductOrderEntity> saveAll(List<CartProductEntity> cartProductEntities, OrderEntity orderEntity) {
        List<CartProductOrderEntity> cartProductOrderEntities = cartProductEntities.stream().map(cartProductEntity -> {
            CartProductOrderEntity cartProductOrderEntity = new CartProductOrderEntity();
            cartProductOrderEntity.setCartProduct(cartProductEntity);
            cartProductOrderEntity.setOrder(orderEntity);
            return cartProductOrderEntity;
        }).toList();
        return saveAll(cartProductOrderEntities);
    }
}
