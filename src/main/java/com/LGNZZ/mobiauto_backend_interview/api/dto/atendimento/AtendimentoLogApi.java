package com.LGNZZ.mobiauto_backend_interview.api.dto.atendimento;

import com.LGNZZ.mobiauto_backend_interview.entity.Enums.TipoAcaoEnum;

import java.time.LocalDateTime;

public record AtendimentoLogApi(
        String tipoAcao,
        LocalDateTime dataAcao
) {
}
