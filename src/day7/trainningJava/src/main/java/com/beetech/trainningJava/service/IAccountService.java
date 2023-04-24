package com.beetech.trainningJava.service;

import com.beetech.trainningJava.config.model.AccountModel;
import com.beetech.trainningJava.entity.AccountEntity;
import com.beetech.trainningJava.entity.CartEntity;

public interface IAccountService {
    /**
     * Lấy thông tin account model của user đang đăng nhập
     * @return AccountModel là thông tin của user đang đăng nhập và là 1 implement của UserDetails
     */
    AccountModel getAccountModel();

    /**
     * Lấy thông tin cart entity của user đang đăng nhập
     * @return CartEntity là thông tin của cart của user đang đăng nhập
     */
    CartEntity getCartEntity();

    /**
     * Lấy thông tin account entity của user đang đăng nhập
     * @return AccountEntity là thông tin của user trong bảng account
     */
    AccountEntity getAccountEntity();

    /**
     * Lưu thông tin account entity vào database
     * @param accountEntity là 1 entity cần lưu vào database
     * @return AccountEntity là thông tin của user trong bảng account sau khi lưu vào database
     */
    AccountEntity save(AccountEntity accountEntity);

    /**
     * Kiểm tra user đã đăng nhập hay chưa
     * @return true nếu đã đăng nhập, false nếu chưa đăng nhập
     * @
     */
    boolean isLogin();
}
