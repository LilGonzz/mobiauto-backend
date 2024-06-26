package com.LGNZZ.mobiauto_backend_interview.api.dto.oportunidade;

public record OportunidadeRequestApi(
        ClienteApi dadosCliente,
        VeiculoApi dadosVeiculo
) {
}
