package com.LGNZZ.mobiauto_backend_interview.controller;

import com.LGNZZ.mobiauto_backend_interview.api.dto.atendimento.AtendimentoApi;
import com.LGNZZ.mobiauto_backend_interview.api.dto.atendimento.AtendimentoRequestApi;
import com.LGNZZ.mobiauto_backend_interview.api.dto.atendimento.AtendimentoResponseApi;
import com.LGNZZ.mobiauto_backend_interview.api.mapper.AtendimentoMapper;
import com.LGNZZ.mobiauto_backend_interview.config.SecurityService;
import com.LGNZZ.mobiauto_backend_interview.entity.Atendimento;
import com.LGNZZ.mobiauto_backend_interview.entity.RevendaUsuario;
import com.LGNZZ.mobiauto_backend_interview.entity.Usuario;
import com.LGNZZ.mobiauto_backend_interview.service.AtendimentoService;
import com.LGNZZ.mobiauto_backend_interview.service.RevendaUsuarioService;
import com.LGNZZ.mobiauto_backend_interview.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/atendimento/{idRevenda}")
public class AtendimentoController {

    @Autowired
    private AtendimentoMapper AtendimentoMapper;
    @Autowired
    private AtendimentoService atendimentoService;
    @Autowired
    private RevendaUsuarioService revendaUsuarioService;
    @Autowired
    private AtendimentoMapper atendimentoMapper;
    @Autowired
    private SecurityService segurancaService;

    @PreAuthorize("@securityService.hasPermission('ROLE_ADMINISTRADOR','ROLE_PROPRIETARIO_' + #idRevenda, 'ROLE_GERENTE_' + #idRevenda, 'ROLE_ASSISTENTE_' + #idRevenda)")
    @GetMapping("/{idOportunidade}")
    public ResponseEntity<List<AtendimentoApi>> obterTodasAtendimentosPorOportunidade(@PathVariable Long idOportunidade) {
        List<Atendimento> Atendimentos = atendimentoService.obterAtendimentosPorOportunidade(idOportunidade);
        List<AtendimentoApi> api = Atendimentos.stream().map(op -> AtendimentoMapper.toAtendimentoApi(op)).toList();

        return ResponseEntity.ok(api);
    }

    @PreAuthorize("@securityService.hasPermission('ROLE_ADMINISTRADOR','ROLE_PROPRIETARIO_' + #idRevenda, 'ROLE_GERENTE_' + #idRevenda, 'ROLE_ASSISTENTE_' + #idRevenda)")
    @GetMapping("/{idAtendimento}")
    public ResponseEntity<AtendimentoApi> obterAtendimentoPorId(@PathVariable(name = "idAtendimento") Long idAtendimento) {
        Atendimento Atendimento = atendimentoService.obterAtendimentosPorId(idAtendimento);
        AtendimentoApi api = AtendimentoMapper.toAtendimentoApi(Atendimento);

        return ResponseEntity.ok(api);
    }
    @PreAuthorize("@securityService.hasPermission('ROLE_ADMINISTRADOR','ROLE_PROPRIETARIO_' + #idRevenda, 'ROLE_GERENTE_' + #idRevenda, 'ROLE_ASSISTENTE_' + #idRevenda)")
    @PostMapping
    public ResponseEntity<Long> criarNovoAtendimento(@PathVariable(name = "idRevenda") Long idRevenda, @RequestBody AtendimentoRequestApi api) {
        Atendimento Atendimento = atendimentoService.criaNovoAtendimento(idRevenda, api);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(Atendimento.getId())
                .toUri();

        return ResponseEntity.created(uri).body(Atendimento.getId());
    }

    @PreAuthorize("@securityService.hasPermission('ROLE_PROPRIETARIO_' + #idRevenda, 'ROLE_GERENTE_' + #idRevenda)")
    @PutMapping("assocciar-atendimento/{idAtendimento}")
    public ResponseEntity<AtendimentoResponseApi> associaUsuarioAtendimento(@PathVariable(name = "idAtendimento") Long idAtendimento, @PathVariable(name = "idRevenda") Long idRevenda,
                                                                            @RequestBody AtendimentoRequestApi api) {
        Atendimento atendimento = atendimentoService.obterAtendimentosPorId(idAtendimento);
        RevendaUsuario revUser = revendaUsuarioService.checkUsuarioRequisitos(api.idUsuario(), idRevenda);
        atendimento = atendimentoService.associarUsuarioAtendimento(atendimento, revUser.getUsuario());
        AtendimentoResponseApi atendimentoApi = atendimentoMapper.toAtendimentoResponseApi(atendimento);
        return ResponseEntity.ok(atendimentoApi);
    }
    @PreAuthorize("@securityService.hasPermission('ROLE_PROPRIETARIO_' + #idRevenda, 'ROLE_GERENTE_' + #idRevenda)")
    @PatchMapping("concluir-atendimento/{idAtendimento}")
    public ResponseEntity<AtendimentoResponseApi> concluirAtendimento(@PathVariable(name = "idAtendimento") Long idAtendimento, @PathVariable(name = "idRevenda") Long idRevenda) {
        Atendimento atendimento = atendimentoService.obterAtendimentosPorId(idAtendimento);
        Usuario usuarioLogado = segurancaService.getUsuarioLogado();
        atendimento = atendimentoService.checkAtendimentoPertenceUsuario(atendimento, usuarioLogado);
        AtendimentoResponseApi atendimentoApi = atendimentoMapper.toAtendimentoResponseApi(atendimento);
        return ResponseEntity.ok(atendimentoApi);
    }
}
