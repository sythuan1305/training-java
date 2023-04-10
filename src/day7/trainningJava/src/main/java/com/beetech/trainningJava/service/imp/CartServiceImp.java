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
    public CartEntity save(CartEntity cartEntity) {
        if (cartEntity == null)
            throw new RuntimeException("cartEntity is null");
        return cartRepository.save(cartEntity);
    }

    @Override
    public CartEntity getOne(Integer cartId) {
        if (cartId == null)
            throw new RuntimeException("cartId is null");
        if (cartRepository.getReferenceById(cartId).getId() == null)
            throw new RuntimeException("cartId not found");
        return cartRepository.getReferenceById(cartId);
    }

    @Override
    public List<CartEntity> findAll() {
        return null;
    }

    @Override
    public CartEntity update(CartEntity cartEntity) {
        return null;
    }

    @Override
    public void delete(Integer cartId) {

    }

    @Override
    public CartEntity checkCartIdNullAndCreateNewIfNull(Integer cartId) {
        return cartId == null ? save(new CartEntity()) : getOne(cartId);
    }

    @Override
    public void saveAccountToCart(AccountEntity accountEntity, CartEntity cartEntity) {
        if (accountEntity != null) {
            if (accountEntity.getCart() == null)
            {
                accountEntity.setCart(cartEntity);
                cartEntity.setAccount(accountEntity);
                save(cartEntity);
            }
        }
    }
}
