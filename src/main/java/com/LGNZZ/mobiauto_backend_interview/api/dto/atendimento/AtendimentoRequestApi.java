package com.LGNZZ.mobiauto_backend_interview.api.dto.atendimento;

import jakarta.validation.constraints.NotNull;

public record AtendimentoRequestApi(
        @NotNull
        Long idOportunidade,
        Long idUsuario
) {
}
