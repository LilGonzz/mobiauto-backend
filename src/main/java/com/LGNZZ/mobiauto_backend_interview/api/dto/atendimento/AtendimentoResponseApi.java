package com.LGNZZ.mobiauto_backend_interview.api.dto.atendimento;

import java.util.List;

public record AtendimentoResponseApi(
        Long idAtendimento,
        Long idOportunidade,
        Long idRevenda,
        Long idUsuario,
        List<AtendimentoLogApi> logs
) {
}
