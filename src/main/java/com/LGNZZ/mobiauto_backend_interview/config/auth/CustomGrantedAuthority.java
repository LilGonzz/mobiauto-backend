package com.LGNZZ.mobiauto_backend_interview.config.auth;

import com.LGNZZ.mobiauto_backend_interview.entity.Enums.RoleEnum;
import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthority implements GrantedAuthority {
    private final RoleEnum role;
    private final Long revendaId;

    public CustomGrantedAuthority(final RoleEnum role, final Long revendaId) {
        this.role = role;
        this.revendaId = revendaId;
    }

    @Override
    public String getAuthority() {
        if(!role.equals(RoleEnum.ADMINISTRADOR))
            return role + "_ROLE_" + revendaId;

        return role + "_ROLE_";
    }
}
