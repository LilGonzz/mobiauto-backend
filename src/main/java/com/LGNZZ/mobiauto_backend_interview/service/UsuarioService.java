package com.LGNZZ.mobiauto_backend_interview.service;

import com.LGNZZ.mobiauto_backend_interview.api.dto.usuario.CriacaoAlteracaoUsuarioApi;
import com.LGNZZ.mobiauto_backend_interview.entity.RevendaUsuario;
import com.LGNZZ.mobiauto_backend_interview.entity.Usuario;
import com.LGNZZ.mobiauto_backend_interview.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RevendaUsuarioService revendaUsuarioService;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }
    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    public Optional<Usuario> getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    public Usuario checkUsuarioEmail(String email){
        Optional<Usuario> usuarioOpt =  this.getUsuarioByEmail(email);
        if (usuarioOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return usuarioOpt.get();
    }
    public Usuario checkUsuarioId(Long id){
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return usuarioOpt.get();
    }
    @Transactional
    public Map<String, List<Long>> softDeleteUsuario(Usuario usuario, Long idRevenda){
        Map<String, List<Long>> mapIds = new HashMap<>();

        if(idRevenda == 0){
            usuario.setActive(false);
            usuario.setUpdatedAt(LocalDateTime.now());
            usuarioRepository.save(usuario);
            mapIds.put("Usuario", List.of(usuario.getId()));
        }
        List<RevendaUsuario> revendaUser = revendaUsuarioService.softDeleteRevendaUsuario(usuario, idRevenda);

        List<Long> idsRevendas = revendaUser.stream().map(rev -> rev.getRevenda().getId()).toList();
        mapIds.put("Revendas", idsRevendas);

        return mapIds;

    }
    @Transactional
    public void alteraUsuario(Usuario usuario, CriacaoAlteracaoUsuarioApi api, BCryptPasswordEncoder bCryptPasswordEncoder, Long idRevenda){
        usuario.setEmail(api.email());
        usuario.setName(api.nome());
        usuario.setSenha(bCryptPasswordEncoder.encode(api.senha()));
        usuario.setUpdatedAt(LocalDateTime.now());
        revendaUsuarioService.alteraRoleUsuarioRevenda(usuario, idRevenda, api.role());
        usuarioRepository.save(usuario);
    }
    @Transactional
    public void deleteUsuario(Usuario usuario, Long idRevenda){
        revendaUsuarioService.deleteRevendaUsuario(usuario, idRevenda);
        if(idRevenda == 0)
            usuarioRepository.delete(usuario);
    }
    public void checkUsuarioLogado(){

    }

}
