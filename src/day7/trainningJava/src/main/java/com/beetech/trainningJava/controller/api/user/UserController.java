package com.beetech.trainningJava.controller.api.user;

import com.beetech.trainningJava.entity.AccountEntity;
import com.beetech.trainningJava.model.ApiResponse;
import com.beetech.trainningJava.model.mappper.AccountEntityDtoMapper;
import com.beetech.trainningJava.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("apiUserController")
@RequestMapping("/api/user/account")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@PreAuthorize("isAuthenticated()")
public class UserController {
    private final IAccountService accountService;

    private final AccountEntityDtoMapper accountEntityDtoMapper;


    @PostMapping("/me")
    public ResponseEntity<Object> me() {
        System.out.println("apiUserController.me");
        AccountEntity accountEntity = accountService.getAccountEntity();
        System.out.println("accountEntity = " + accountEntity);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), true, "Success", accountEntityDtoMapper.apply(accountEntity)));
    }
}
