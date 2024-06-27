package com.LGNZZ.mobiauto_backend_interview.controller;

import com.LGNZZ.mobiauto_backend_interview.api.dto.oportunidade.AlteracaoOportunidadeApi;
import com.LGNZZ.mobiauto_backend_interview.api.dto.oportunidade.OportunidadeApi;
import com.LGNZZ.mobiauto_backend_interview.api.dto.oportunidade.OportunidadeRequestApi;
import com.LGNZZ.mobiauto_backend_interview.api.dto.usuario.CriacaoAlteracaoUsuarioApi;
import com.LGNZZ.mobiauto_backend_interview.api.dto.usuario.SoftDeleteResponseApi;
import com.LGNZZ.mobiauto_backend_interview.api.mapper.OportunidadeMapper;
import com.LGNZZ.mobiauto_backend_interview.entity.Oportunidade;
import com.LGNZZ.mobiauto_backend_interview.entity.Usuario;
import com.LGNZZ.mobiauto_backend_interview.service.OportunidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/{idRevenda}/oportunidade")
public class OportunidadeController {

    @Autowired
    private OportunidadeService oportunidadeService;
    @Autowired
    private OportunidadeMapper oportunidadeMapper;

    @PreAuthorize("@securityService.hasPermission('ROLE_ADMINISTRADOR','ROLE_PROPRIETARIO_' + #idRevenda, 'ROLE_GERENTE_' + #idRevenda, 'ROLE_ASSISTENTE_' + #idRevenda)")
    @GetMapping
    public ResponseEntity<List<OportunidadeApi>> obterTodasOportunidadesPorRevenda(@PathVariable Long idRevenda) {
        List<Oportunidade> oportunidades = oportunidadeService.obterOportunidadesPorRevenda(idRevenda);
        List<OportunidadeApi> api = oportunidades.stream().map(op -> oportunidadeMapper.toOportunidadeApi(op)).toList();

        return ResponseEntity.ok(api);
    }

    @PreAuthorize("@securityService.hasPermission('ROLE_ADMINISTRADOR','ROLE_PROPRIETARIO_' + #idRevenda, 'ROLE_GERENTE_' + #idRevenda, 'ROLE_ASSISTENTE_' + #idRevenda)")
    @GetMapping("/{idOportunidade}")
    public ResponseEntity<OportunidadeApi> obterOportunidadePorId(@PathVariable(name = "idOportunidade") Long idOportunidade) {
        Oportunidade oportunidade = oportunidadeService.obterOportunidadePorId(idOportunidade);
        OportunidadeApi api = oportunidadeMapper.toOportunidadeApi(oportunidade);

        return ResponseEntity.ok(api);
    }
    @PreAuthorize("@securityService.hasPermission('ROLE_ADMINISTRADOR','ROLE_PROPRIETARIO_' + #idRevenda, 'ROLE_GERENTE_' + #idRevenda, 'ROLE_ASSISTENTE_' + #idRevenda)")
    @PostMapping
    public ResponseEntity<Long> criarNovaOportunidade(@PathVariable Long idRevenda, @RequestBody OportunidadeRequestApi api) {
        Oportunidade oportunidade = oportunidadeService.criaNovaOportunidade(idRevenda, api);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(oportunidade.getId())
                .toUri();

        return ResponseEntity.created(uri).body(oportunidade.getId());
    }

    @PreAuthorize("@securityService.hasPermission('ROLE_ADMINISTRADOR','ROLE_PROPRIETARIO_' + #idRevenda, 'ROLE_GERENTE_' + #idRevenda, 'ROLE_ASSISTENTE_' + #idRevenda)")
    @PutMapping("/{idRevenda}")
    public ResponseEntity<String> alterarOportunidade(@RequestBody AlteracaoOportunidadeApi api, @PathVariable(name = "idRevenda") Long idRevenda){
        oportunidadeService.alteraOportunidade(api);
        return ResponseEntity.ok("Oportunidade alterado com sucesso!");
    }

    @PreAuthorize("@securityService.hasPermission('ROLE_ADMINISTRADOR','ROLE_PROPRIETARIO_' + #idRevenda, 'ROLE_GERENTE_' + #idRevenda, 'ROLE_ASSISTENTE_' + #idRevenda)")
    @DeleteMapping("delete/{idOportunidade}")
    public ResponseEntity<Void> deletaUsuario(@PathVariable(name = "idRevenda") Long idRevenda, @PathVariable(name = "idOportunidade") Long idOportunidade){
        oportunidadeService.deletaOportunidade(idOportunidade);
        return ResponseEntity.noContent().build();
    }

}
