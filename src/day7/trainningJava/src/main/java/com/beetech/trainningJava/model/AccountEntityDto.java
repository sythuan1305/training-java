package com.beetech.trainningJava.model;

import com.beetech.trainningJava.enums.Role;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.beetech.trainningJava.entity.AccountEntity}
 */
@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class AccountEntityDto implements Serializable {
    String username;
    String name;
    Role role;
    String email;
}