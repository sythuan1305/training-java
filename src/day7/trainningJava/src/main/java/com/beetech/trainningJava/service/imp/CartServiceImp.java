package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.AccountEntity;
import com.beetech.trainningJava.entity.CartEntity;
import com.beetech.trainningJava.repository.CartRepository;
import com.beetech.trainningJava.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImp implements ICartService {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public CartEntity saveCartEntity(CartEntity cartEntity) {
        if (cartEntity == null)
            throw new RuntimeException("cartEntity is null");
        return cartRepository.save(cartEntity);
    }

    @Override
    public CartEntity getCartEntityById(Integer cartId) {
        if (cartId == null)
            throw new RuntimeException("cartId is null");
        if (cartRepository.getReferenceById(cartId).getId() == null)
            throw new RuntimeException("cartId not found");
        return cartRepository.getReferenceById(cartId);
    }
}
