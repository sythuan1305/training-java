package com.beetech.trainningJava.model.mappper;

import com.beetech.trainningJava.entity.AccountEntity;
import com.beetech.trainningJava.model.AccountEntityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountEntityDtoMapper implements Function<AccountEntity, AccountEntityDto> {
    @Override
    public AccountEntityDto apply(AccountEntity accountEntity) {
        return new AccountEntityDto(
                accountEntity.getRefreshTokenEntity().getUsername(),
                accountEntity.getName(),
                accountEntity.getRole(),
                accountEntity.getEmail());
    }
}
