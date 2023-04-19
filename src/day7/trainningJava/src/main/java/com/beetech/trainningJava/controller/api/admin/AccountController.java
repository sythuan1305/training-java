package com.beetech.trainningJava.controller.api.admin;

import com.beetech.trainningJava.entity.AccountEntity;
import com.beetech.trainningJava.model.ApiResponse;
import com.beetech.trainningJava.repository.AccountRepository;
import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController("apiAdminAccountController")
@RequestMapping("/api/admin/account")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;


    @GetMapping("/test")
    public ResponseEntity<ApiResponse> getAccountByUserName(@RequestParam(value = "username") String username) {
        List<AccountEntity> accountEntities = accountRepository.findAllByUsernameNative(username);
        System.out.println("accountEntities = " + accountEntities);
        return ResponseEntity.ok(new ApiResponse(true ,accountEntities));
    }

}
