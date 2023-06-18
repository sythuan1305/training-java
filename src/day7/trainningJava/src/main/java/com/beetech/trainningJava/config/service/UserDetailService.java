package com.beetech.trainningJava.config.service;

import com.beetech.trainningJava.config.model.AccountModel;
import com.beetech.trainningJava.config.model.RoleModel;
import com.beetech.trainningJava.entity.AccountEntity;
import com.beetech.trainningJava.entity.RefreshTokenEntity;
import com.beetech.trainningJava.repository.AccountRepository;
import com.beetech.trainningJava.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Class này dùng để tạo ra đối tượng UserDetails từ username. <br>
 * UserDetails chứa thông tin của user và quyền của user
 *
 * @see UserDetailsService
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserDetailService implements UserDetailsService {
    private final AccountRepository accountRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByUsername(username);
        System.out.println("refreshTokenEntity: " + refreshTokenEntity);
        // Kiem tra xem username co ton tai trong DB hay khong?
        AccountEntity account = accountRepository.findByRefreshTokenEntity(refreshTokenEntity);

        System.out.println("account: " + account);
        if (account == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Lay quyen cua user
        List<RoleModel> roles = new ArrayList<>();
        roles.add(new RoleModel(account.getRole()));

        return new AccountModel(account, roles);
    }
}
