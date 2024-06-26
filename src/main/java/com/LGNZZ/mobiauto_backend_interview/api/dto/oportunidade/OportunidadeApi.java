package com.LGNZZ.mobiauto_backend_interview.api.dto.oportunidade;

import com.LGNZZ.mobiauto_backend_interview.entity.Enums.SituacaoOportunidadeEnum;

public record OportunidadeApi(
        Long idOportunidade,
        Long idRevenda,
        ClienteApi dadosCliente,
        VeiculoApi dadosVeiculo,
        SituacaoOportunidadeEnum situacao,
        String descricaoConclusao
) {
}
