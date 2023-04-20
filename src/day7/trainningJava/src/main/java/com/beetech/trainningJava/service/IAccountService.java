package com.beetech.trainningJava.service;

import com.beetech.trainningJava.config.model.AccountModel;
import com.beetech.trainningJava.entity.AccountEntity;
import com.beetech.trainningJava.entity.CartEntity;

public interface IAccountService {
    AccountModel getAccountModel();

    CartEntity getCartEntity();

    AccountEntity getAccountEntity();

    AccountEntity save(AccountEntity accountEntity);

    boolean isLogin();
}
