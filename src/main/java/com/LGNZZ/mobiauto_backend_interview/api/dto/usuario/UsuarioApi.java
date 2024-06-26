package com.LGNZZ.mobiauto_backend_interview.api.dto.usuario;

import com.LGNZZ.mobiauto_backend_interview.api.dto.RolesRevendaApi;

import java.util.List;

public record UsuarioApi(
        Long idUsuario,
        String nome,
        String email
) {
}
