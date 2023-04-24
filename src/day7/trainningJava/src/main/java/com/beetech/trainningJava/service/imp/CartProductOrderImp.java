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
    public CartProductOrderEntity saveCartProductOrderEntityByEntity(CartProductOrderEntity cartProductOrderEntity) {
        return cartProductOrderRepository.save(cartProductOrderEntity);
    }

    @Override
    public List<CartProductOrderEntity> updateCartProductOrderEntityListAfterBoughtByOrderEntity(OrderEntity orderEntity) {
        // update order status to PAID
        orderEntity.setPaymentStatus(PaymentStatus.PAID);
        // update cart product bought status to true
        List<CartProductOrderEntity> cartProductOrderEntities = findCartProductOrderEntityListByOrderEntity(orderEntity);
        return cartProductOrderEntities.stream().map(cartProductOrderEntity -> {
            cartProductOrderEntity.setOrder(orderEntity);
            CartProductEntity cartProductEntity = cartProductOrderEntity.getCartProduct();
            cartProductEntity.setBought(true);
            return saveCartProductOrderEntityByEntity(cartProductOrderEntity);
        }).toList();
    }

    @Override
    public List<CartProductOrderEntity> findCartProductOrderEntityListByOrderEntity(OrderEntity orderEntity) {
        return cartProductOrderRepository.findAllByOrderId(orderEntity.getId());
    }

    @Override
    public List<CartProductOrderEntity> saveCartProductOrderEntityListByEntityList(List<CartProductOrderEntity> cartProductOrderEntities) {
        return cartProductOrderRepository.saveAll(cartProductOrderEntities);
    }

    @Override
    public List<CartProductOrderEntity> saveCartProductOrderEntityListByCartProductEntityListAndOrderEntity(List<CartProductEntity> cartProductEntities, OrderEntity orderEntity) {
        List<CartProductOrderEntity> cartProductOrderEntities = cartProductEntities.stream().map(cartProductEntity -> {
            // create cart product order entity and set cart product and order
            CartProductOrderEntity cartProductOrderEntity = new CartProductOrderEntity();
            cartProductOrderEntity.setCartProduct(cartProductEntity);
            cartProductOrderEntity.setOrder(orderEntity);
            return cartProductOrderEntity;
        }).toList();
        return saveCartProductOrderEntityListByEntityList(cartProductOrderEntities);
    }
}
