package com.beetech.trainningJava.controller.api.admin;

import com.beetech.trainningJava.entity.AccountEntity;
import com.beetech.trainningJava.model.ApiResponse;
import com.beetech.trainningJava.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller này dùng để xử lý các request liên quan đến tài khoản với quyền admin
 */
@RestController("apiAdminAccountController")
@RequestMapping("/api/admin/account")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/test")
    public ResponseEntity<ApiResponse> getAccountByUserName(@RequestParam(value = "username") String username) {
        List<AccountEntity> accountEntities = accountRepository.findAllByUsernameNative(username);
        return ResponseEntity.ok(new ApiResponse(true, accountEntities));
    }
}
