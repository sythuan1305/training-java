package com.beetech.trainningJava.config.service;

import com.beetech.trainningJava.config.model.AccountModel;
import com.beetech.trainningJava.config.model.RoleModel;
import com.beetech.trainningJava.entity.AccountEntity;
import com.beetech.trainningJava.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Kiem tra xem username co ton tai trong DB hay khong?
        AccountEntity account = accountRepository.findByUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Lay quyen cua user
        List<RoleModel> roles = new ArrayList<>();
        roles.add(new RoleModel(account.getRole()));

        return new AccountModel(account, roles);
    }
}
