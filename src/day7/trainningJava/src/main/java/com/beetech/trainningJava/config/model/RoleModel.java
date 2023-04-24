package com.beetech.trainningJava.config.model;

import com.beetech.trainningJava.enums.Role;
import org.springframework.security.core.GrantedAuthority;

/**
 * Class này dùng để tạo ra đối tượng GrantedAuthority từ Role. <br>
 * GrantedAuthority chứa thông tin quyền của user
 * @see GrantedAuthority
 */
public class RoleModel implements GrantedAuthority {
    private Role roleId;

    private String roleName;

    public RoleModel(Role roleId) {
        this.roleId = roleId;
        this.roleName = roleId.name().toUpperCase();
    }

    @Override
    public String getAuthority() {
        return roleName;
    }

    public Role getRole() {
        return roleId;
    }

    public void setRole(Role role) {
        this.roleId = role;
    }
}
