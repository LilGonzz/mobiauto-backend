package com.LGNZZ.mobiauto_backend_interview.api.mapper;

import com.LGNZZ.mobiauto_backend_interview.api.dto.RevendaApi;
import com.LGNZZ.mobiauto_backend_interview.api.dto.oportunidade.ClienteApi;
import com.LGNZZ.mobiauto_backend_interview.api.dto.oportunidade.OportunidadeApi;
import com.LGNZZ.mobiauto_backend_interview.api.dto.oportunidade.VeiculoApi;
import com.LGNZZ.mobiauto_backend_interview.entity.Cliente;
import com.LGNZZ.mobiauto_backend_interview.entity.Oportunidade;
import com.LGNZZ.mobiauto_backend_interview.entity.Revenda;
import com.LGNZZ.mobiauto_backend_interview.entity.Veiculo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OportunidadeMapper {
    @Mapping(source="id", target = "idOportunidade")
    @Mapping(source="revenda.id", target = "idRevenda")
    @Mapping(expression = "java(getDadosCliente(oportunidade.getCliente()))", target = "dadosCliente")
    @Mapping(expression = "java(getDadosVeiculo(oportunidade.getVeiculo()))", target = "dadosVeiculo")
    OportunidadeApi toOportunidadeApi(Oportunidade oportunidade);

    default ClienteApi getDadosCliente(Cliente cliente) {
        return new ClienteApi(cliente.getName(), cliente.getEmail(), cliente.getTelefone());
    }
    default VeiculoApi getDadosVeiculo(Veiculo veiculo) {
        return new VeiculoApi(veiculo.getMarca(), veiculo.getVersao(), veiculo.getModelo(), veiculo.getAnoModelo());
    }
}
