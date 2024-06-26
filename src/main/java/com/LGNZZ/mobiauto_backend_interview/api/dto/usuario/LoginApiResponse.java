package com.LGNZZ.mobiauto_backend_interview.api.dto.usuario;

import java.time.Instant;

public record LoginApiResponse(
        String jwt,
        Long expiresIn
) {
}
