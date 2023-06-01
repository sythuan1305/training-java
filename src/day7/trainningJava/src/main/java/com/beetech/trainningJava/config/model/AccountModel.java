package com.beetech.trainningJava.config.model;

import com.beetech.trainningJava.entity.AccountEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Class này dùng để tạo ra đối tượng UserDetails từ AccountEntity. <br>
 * UserDetails chứa thông tin của user và quyền của user
 * @see UserDetails
 */
@Getter
@Setter
public class AccountModel implements UserDetails {
    private AccountEntity account;

    private Collection<RoleModel> roles;

    public AccountModel(AccountEntity account, Collection<RoleModel> roles) {
        super();
        this.account = account;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getRefreshTokenEntity().getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !account.isBlocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return account.isVerify();
    }
}
