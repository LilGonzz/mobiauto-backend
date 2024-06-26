package com.LGNZZ.mobiauto_backend_interview.controller;

import com.LGNZZ.mobiauto_backend_interview.api.dto.usuario.*;
import com.LGNZZ.mobiauto_backend_interview.api.mapper.UsuarioMapper;
import com.LGNZZ.mobiauto_backend_interview.config.SecurityService;
import com.LGNZZ.mobiauto_backend_interview.entity.Usuario;
import com.LGNZZ.mobiauto_backend_interview.service.RevendaUsuarioService;
import com.LGNZZ.mobiauto_backend_interview.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/v1/users")
public class UsuarioController {
    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RevendaUsuarioService revendaUsuarioService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UsuarioMapper usuarioMapper;

    @PreAuthorize("@securityService.hasPermission('ROLE_ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<UsuarioApi>> obterTodosUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        List<UsuarioApi> api = usuarios.stream().map(user -> usuarioMapper.toUsuarioApi(user)).toList();

        return ResponseEntity.ok(api);
    }

    @PreAuthorize("@securityService.hasPermission('ROLE_ADMINISTRADOR')")
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioApi> obterUsuarioPorId(@RequestParam("idUsuario") Long id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        UsuarioApi api = usuarioMapper.toUsuarioApi(usuario);

        return ResponseEntity.ok(api);
    }

    @PreAuthorize("@securityService.hasPermission('ROLE_ADMINISTRADOR','ROLE_PROPRIETARIO_' + #idRevenda, 'ROLE_GERENTE_' + #idRevenda)")
    @PostMapping("/novo-usuario/{idRevenda}")
    public ResponseEntity<Long> novoUsuario(@RequestBody CriacaoAlteracaoUsuarioApi usuarioApi, @PathVariable Long idRevenda){
        usuarioService.checkUsuarioEmail(usuarioApi.email());
        String senhaCriptografada = bCryptPasswordEncoder.encode(usuarioApi.senha());
        Usuario usuario = new Usuario(usuarioApi.nome(), usuarioApi.email(), senhaCriptografada);
        usuario = usuarioService.salvarUsuario(usuario);
        revendaUsuarioService.associaUsuarioRevenda(usuario, idRevenda, usuarioApi.role());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuario.getId())
                .toUri();

        return ResponseEntity.created(uri).body(usuario.getId());
    }
    @PreAuthorize("@securityService.hasPermission('ROLE_ADMINISTRADOR','ROLE_PROPRIETARIO_' + #idRevenda)")
    @PutMapping("/{idRevenda}")
    public ResponseEntity<String> alterarUsuario(@RequestBody CriacaoAlteracaoUsuarioApi usuarioApi, @PathVariable Long idRevenda){
        Usuario usuario = usuarioService.checkUsuarioEmail(usuarioApi.email());
        usuarioService.alteraUsuario(usuario, usuarioApi, bCryptPasswordEncoder, idRevenda);

        return ResponseEntity.ok("Usuario alterado com sucesso!");
    }
    @PreAuthorize("@securityService.hasPermission('ROLE_ADMINISTRADOR','ROLE_PROPRIETARIO_' + #idRevenda)")
    @PatchMapping("soft-delete/{idUsuario}")
    public ResponseEntity<List<SoftDeleteResponseApi>> softDeleteUsuario(@PathVariable(name = "idUsuario") Long idUsuario,
                                                                   @RequestParam(name = "idRevenda", defaultValue  = "0", required = false) Long idRevenda){
        Usuario usuario = usuarioService.checkUsuarioId(idUsuario);
        Map<String, List<Long>> mapIdsAlterados = usuarioService.softDeleteUsuario(usuario, idRevenda);

        List<SoftDeleteResponseApi> api = new ArrayList<>();
        mapIdsAlterados.forEach((key, value) -> {
            api.add(usuarioMapper.toSoftDeleteResponseApi(key, value));
        });

        return ResponseEntity.ok(api);
    }

    @PreAuthorize("@securityService.hasPermission('ROLE_ADMINISTRADOR','ROLE_PROPRIETARIO_' + #idRevenda)")
    @PatchMapping("delete/{idUsuario}/{idRevenda}")
    public ResponseEntity<Void> deletaUsuario(@PathVariable(name = "idRevenda") Long idRevenda, @PathVariable(name = "idUsuario") Long idUsuario){
        Usuario usuario = usuarioService.checkUsuarioId(idUsuario);
        usuarioService.deleteUsuario(usuario, idRevenda);

        return ResponseEntity.noContent().build();
    }
}
