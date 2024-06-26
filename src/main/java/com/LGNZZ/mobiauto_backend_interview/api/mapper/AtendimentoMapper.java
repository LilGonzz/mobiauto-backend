package com.LGNZZ.mobiauto_backend_interview.api.mapper;

import com.LGNZZ.mobiauto_backend_interview.api.dto.atendimento.AtendimentoApi;
import com.LGNZZ.mobiauto_backend_interview.api.dto.atendimento.AtendimentoLogApi;
import com.LGNZZ.mobiauto_backend_interview.api.dto.atendimento.AtendimentoResponseApi;
import com.LGNZZ.mobiauto_backend_interview.entity.Atendimento;
import com.LGNZZ.mobiauto_backend_interview.entity.logs.AtendimentoLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AtendimentoMapper {


    AtendimentoApi toAtendimentoApi(Atendimento atendimento);

    @Mapping(source = "id", target = "idAtendimento")
    @Mapping(source = "oportunidade.id", target = "idOportunidade")
    @Mapping(source = "revenda.id", target = "idRevenda")
    @Mapping(source = "usuario.id", target = "idUsuario")
    @Mapping(expression = "java(toLogsApi(atendimento.getLogs()))", target = "logs")
    AtendimentoResponseApi toAtendimentoResponseApi(Atendimento atendimento);


    default List<AtendimentoLogApi> toLogsApi(List<AtendimentoLog> atendimentoLogs) {
        List<AtendimentoLogApi> logs = new ArrayList<>();
        atendimentoLogs.forEach(log ->
                logs.add(new AtendimentoLogApi(log.getTipoAcao().name(), log.getDataAcao())));
        return logs;
    }
}
