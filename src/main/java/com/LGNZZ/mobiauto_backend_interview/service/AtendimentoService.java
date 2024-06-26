package com.LGNZZ.mobiauto_backend_interview.service;

import com.LGNZZ.mobiauto_backend_interview.api.dto.atendimento.AtendimentoRequestApi;
import com.LGNZZ.mobiauto_backend_interview.entity.Atendimento;
import com.LGNZZ.mobiauto_backend_interview.entity.Enums.TipoAcaoEnum;
import com.LGNZZ.mobiauto_backend_interview.entity.Oportunidade;
import com.LGNZZ.mobiauto_backend_interview.entity.Revenda;
import com.LGNZZ.mobiauto_backend_interview.entity.Usuario;
import com.LGNZZ.mobiauto_backend_interview.entity.logs.AtendimentoLog;
import com.LGNZZ.mobiauto_backend_interview.repository.AtendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AtendimentoService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;
    @Autowired
    private OportunidadeService oportunidadeService;
    @Autowired
    private RevendaService revendaService;
    @Autowired
    private UsuarioService usuarioService;

    public List<Atendimento> obterAtendimentosPorOportunidade(Long idOportunidade){
        return atendimentoRepository.findAllByIdOportunidade(idOportunidade).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Atendimento obterAtendimentosPorId(Long idAtendimento){
        return atendimentoRepository.findById(idAtendimento).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Atendimento criaNovoAtendimento(Long idRevenda, AtendimentoRequestApi api){
        Oportunidade oportunidade = oportunidadeService.obterOportunidadePorId(api.idOportunidade());
        Revenda revenda = revendaService.obterPorId(idRevenda);
        Usuario usuario = api.idUsuario() != null ? usuarioService.getUsuarioById(api.idUsuario()) : null;

        Atendimento atendimento = new Atendimento(oportunidade, revenda, usuario);
        AtendimentoLog log = usuario != null ? new AtendimentoLog(atendimento, TipoAcaoEnum.NOVA_ATRIBUICAO) : new AtendimentoLog(atendimento, TipoAcaoEnum.AGUARDANDO_RESPONSAVEL);

        atendimento.addLog(log);
        return atendimentoRepository.save(atendimento);
    }

    public Atendimento associarUsuarioAtendimento(Atendimento atendimento, Usuario usuario){
        AtendimentoLog log = atendimento.getUsuario() == null ? new AtendimentoLog(atendimento, TipoAcaoEnum.NOVA_ATRIBUICAO) : new AtendimentoLog(atendimento, TipoAcaoEnum.ALTERACAO_RESPONSAVEL);
        atendimento.setUsuario(usuario);
        atendimento.addLog(log);
        return atendimentoRepository.save(atendimento);
    }

    public Atendimento checkAtendimentoPertenceUsuario(Atendimento atendimento, Usuario usuario){
        if (!atendimento.getUsuario().equals(usuario)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        AtendimentoLog atendimentoLog = new AtendimentoLog(atendimento, TipoAcaoEnum.CONCLUSAO);
        atendimento.setActive(false);
        atendimento.setUpdatedAt(LocalDateTime.now());
        atendimento.addLog(atendimentoLog);

        return atendimentoRepository.save(atendimento);
    }
}
