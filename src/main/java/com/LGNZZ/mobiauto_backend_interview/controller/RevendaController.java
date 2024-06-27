package com.LGNZZ.mobiauto_backend_interview.controller;

import com.LGNZZ.mobiauto_backend_interview.api.dto.RevendaApi;
import com.LGNZZ.mobiauto_backend_interview.api.dto.revenda.CriacaoAteracaoRevendaApi;
import com.LGNZZ.mobiauto_backend_interview.api.dto.usuario.CriacaoAlteracaoUsuarioApi;
import com.LGNZZ.mobiauto_backend_interview.api.dto.usuario.SoftDeleteResponseApi;
import com.LGNZZ.mobiauto_backend_interview.api.mapper.RevendaMapper;
import com.LGNZZ.mobiauto_backend_interview.entity.Revenda;
import com.LGNZZ.mobiauto_backend_interview.entity.Usuario;
import com.LGNZZ.mobiauto_backend_interview.service.RevendaService;
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
@RequestMapping("api/v1/revenda")
public class RevendaController {

    @Autowired
    private RevendaService revendaService;
    @Autowired
    private RevendaMapper revendaMapper;

    @PreAuthorize("@securityService.hasPermission('ROLE_ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<RevendaApi>> obterTodasRevendas() {
        List<Revenda> revendas = revendaService.findAll();
        List<RevendaApi> revendaApis = revendas.stream().map(revenda -> revendaMapper.revendaToApi(revenda)).toList();
        return ResponseEntity.ok(revendaApis);
    }
    @PreAuthorize("@securityService.hasPermission('ROLE_ADMINISTRADOR')")
    @GetMapping("/{idRevenda}")
    public ResponseEntity<RevendaApi> obterRevendaPorId(@PathVariable("idRevenda") Long idRevenda) {
        Revenda revenda = revendaService.obterPorId(idRevenda);
        RevendaApi revendaApi = revendaMapper.revendaToApi(revenda);
        return ResponseEntity.ok(revendaApi);
    }
    @PreAuthorize("@securityService.hasPermission('ROLE_ADMINISTRADOR')")
    @PostMapping("/nova-revenda")
    public ResponseEntity<Long> novaRevenda(@RequestBody CriacaoAteracaoRevendaApi api){
        Revenda revenda = revendaService.criarNovaRevenda(api);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(revenda.getId())
                .toUri();
        return ResponseEntity.created(uri).body(revenda.getId());
    }
    @PreAuthorize("@securityService.hasPermission('ROLE_ADMINISTRADOR','ROLE_PROPRIETARIO_' + #idRevenda)")
    @PutMapping("/{idRevenda}")
    public ResponseEntity<String> alterarRevenda(@RequestBody CriacaoAteracaoRevendaApi api, @PathVariable Long idRevenda){
        Revenda revenda = revendaService.obterPorId(idRevenda);
        revendaService.alteraRevenda(revenda, api);
        return ResponseEntity.ok("Revenda alterada com sucesso!");
    }
    @PreAuthorize("@securityService.hasPermission('ROLE_ADMINISTRADOR','ROLE_PROPRIETARIO_' + #idRevenda)")
    @PatchMapping("/soft-delete/{idRevenda}")
    public ResponseEntity<Long> softDeleteRevenda(@PathVariable Long idRevenda){
        Revenda revenda = revendaService.obterPorId(idRevenda);
        revendaService.softDeleteRevenda(revenda);
        return ResponseEntity.ok(revenda.getId());
    }
}
