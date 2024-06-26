package com.LGNZZ.mobiauto_backend_interview.api.dto.usuario;

import com.LGNZZ.mobiauto_backend_interview.entity.Enums.RoleEnum;

import java.util.List;

public record SoftDeleteResponseApi(
        String tipoDadoAlterado,
        List<Long> ids
) {
}
