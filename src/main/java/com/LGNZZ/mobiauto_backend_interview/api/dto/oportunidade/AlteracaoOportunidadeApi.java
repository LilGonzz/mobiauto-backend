package com.LGNZZ.mobiauto_backend_interview.api.dto.oportunidade;

import com.LGNZZ.mobiauto_backend_interview.entity.Enums.SituacaoOportunidadeEnum;
import jakarta.persistence.Column;

public record AlteracaoOportunidadeApi(
        Long idOportunidade,
        ClienteApi dadosCliente,
        VeiculoApi dadosVeiculo,
        SituacaoOportunidadeEnum situacao,
        String descricaoConclusao
) {
}
