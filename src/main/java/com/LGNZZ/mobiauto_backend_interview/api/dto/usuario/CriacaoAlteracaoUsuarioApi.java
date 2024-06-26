package com.LGNZZ.mobiauto_backend_interview.api.dto.usuario;

import com.LGNZZ.mobiauto_backend_interview.entity.Enums.RoleEnum;

public record CriacaoAlteracaoUsuarioApi(
        String nome,
        String email,
        String senha,
        RoleEnum role
) {
}
