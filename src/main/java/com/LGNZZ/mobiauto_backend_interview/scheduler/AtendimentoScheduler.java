package com.LGNZZ.mobiauto_backend_interview.scheduler;

import com.LGNZZ.mobiauto_backend_interview.entity.Atendimento;
import com.LGNZZ.mobiauto_backend_interview.entity.Enums.TipoAcaoEnum;
import com.LGNZZ.mobiauto_backend_interview.entity.Revenda;
import com.LGNZZ.mobiauto_backend_interview.entity.Usuario;
import com.LGNZZ.mobiauto_backend_interview.entity.logs.AtendimentoLog;
import com.LGNZZ.mobiauto_backend_interview.service.AtendimentoLogService;
import com.LGNZZ.mobiauto_backend_interview.service.AtendimentoService;
import com.LGNZZ.mobiauto_backend_interview.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AtendimentoScheduler {

    @Autowired
    private AtendimentoService atendimentoService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AtendimentoLogService atendimentoLogService;

    @Scheduled(fixedRate = 200000)
    public void vinculaAtendimentoAosResponsaveis(){
        List<Atendimento> atendimentosSemResponsavel = atendimentoService.obterAtendimentosSemResponsavel();
        Map<Revenda, List<Atendimento>> revendaAtendimentoMap = atendimentosSemResponsavel.stream().collect(Collectors.groupingBy(atendimento -> atendimento.getRevenda()));

        revendaAtendimentoMap.forEach((revKey, atendValue) -> {
            List<Usuario> usuarios = usuarioService.obterUsuarioPorRevenda(revKey);
            Map<Usuario, Long> mapUsuarioQtdeAtendimento = new HashMap<>();
            usuarios.forEach(usuario -> {
                int quantidadeAtendimento = atendimentoService.obterQuantidadeAtendimentoPorUsuario(usuario, revKey).size();
                mapUsuarioQtdeAtendimento.put(usuario, (long) quantidadeAtendimento);
            });
            Map<Usuario, Long> mapUsuarioAtendimentoOrdenado = ordenaMap(mapUsuarioQtdeAtendimento);

            mapUsuarioAtendimentoOrdenado.forEach((key, value) -> {
                if(atendimentosSemResponsavel.isEmpty())
                    return;

                Atendimento atendimento = atendimentosSemResponsavel.get(0);
                atendimento.setUsuario(key);
                atendimento.setUpdatedAt(LocalDateTime.now());

                AtendimentoLog atendimentoLog = new AtendimentoLog(atendimento, TipoAcaoEnum.NOVA_ATRIBUICAO);
                atendimentoLogService.salvarLog(atendimentoLog);

                atendimento.addLog(atendimentoLog);
                atendimentoService.salvarAtendimento(atendimento);
            });
        });
    }

    private Map<Usuario, Long> ordenaMap(Map<Usuario, Long> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
