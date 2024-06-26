package com.LGNZZ.mobiauto_backend_interview.api.dto;

import com.LGNZZ.mobiauto_backend_interview.entity.Enums.RoleEnum;

public record RolesRevendaApi(
        Long idRevenda,
        String nameRevenda,
        RoleEnum role
) {
}
