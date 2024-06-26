package com.LGNZZ.mobiauto_backend_interview.api.dto.usuario;

public record LoginApiRequest(
        String email,
        String senha
) {
}
