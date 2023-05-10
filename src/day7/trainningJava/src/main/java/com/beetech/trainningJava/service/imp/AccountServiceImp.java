package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.config.model.AccountModel;
import com.beetech.trainningJava.entity.AccountEntity;
import com.beetech.trainningJava.entity.CartEntity;
import com.beetech.trainningJava.repository.AccountRepository;
import com.beetech.trainningJava.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AccountServiceImp implements IAccountService {
    private final AccountRepository accountRepository;

    @Override
    public AccountModel getAccountModel() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            return (AccountModel) authentication.getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CartEntity getCartEntity() {
        if (getAccountEntity() != null) {
            return getAccountEntity().getCart();
        }
        return null;
    }

    @Override
    public AccountEntity getAccountEntity() {
        if (getAccountModel() != null) {
            return getAccountModel().getAccount();
        }
        return null;
    }

    @Override
    public AccountEntity save(AccountEntity accountEntity) {
        return accountRepository.save(accountEntity);
    }

    @Override
    public boolean isLogin() {
        return getAccountModel() != null;
    }
}
