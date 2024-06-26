package com.LGNZZ.mobiauto_backend_interview.service;

import com.LGNZZ.mobiauto_backend_interview.entity.Enums.RoleEnum;
import com.LGNZZ.mobiauto_backend_interview.entity.Revenda;
import com.LGNZZ.mobiauto_backend_interview.entity.RevendaUsuario;
import com.LGNZZ.mobiauto_backend_interview.entity.Usuario;
import com.LGNZZ.mobiauto_backend_interview.repository.RevendaUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RevendaUsuarioService {

    @Autowired
    private RevendaUsuarioRepository revendaUsuarioRepository;
    @Autowired
    private RevendaService revendaService;

    public void associaUsuarioRevenda(Usuario usuario, Long idRevenda, RoleEnum role) {
        Revenda revenda = revendaService.obterPorId(idRevenda);
        List<RevendaUsuario> revendaUser = revendaUsuarioRepository.findAllByRevenda(revenda);

        revendaUser.stream()
                .filter(revUser ->
                        revUser.getUsuario().getEmail().equals(usuario.getEmail()))
                .findFirst()
                .ifPresent(usr -> {
                    throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
                });

        RevendaUsuario revendaUsuario = new RevendaUsuario(usuario, revenda, role);
        revendaUsuarioRepository.save(revendaUsuario);
    }

    public List<RevendaUsuario> obterRevendasPorUsuario(Usuario usuario){
        return revendaUsuarioRepository.findAllByUsuario(usuario);
    }

    public List<RevendaUsuario> obterRevendasUsuarioPorRevendaId(Long idRevenda){
        return revendaUsuarioRepository.findAllByRevendaId(idRevenda);
    }


    public void alteraRoleUsuarioRevenda(Usuario usuario, Long idRevenda, RoleEnum role){
        Revenda revenda = revendaService.obterPorId(idRevenda);
        RevendaUsuario revendaUser = revendaUsuarioRepository.findByRevendaAndUsuario(revenda, usuario);
        revendaUser.setRole(role);
        revendaUsuarioRepository.save(revendaUser);
    }

    public List<RevendaUsuario> softDeleteRevendaUsuario(Usuario usuario, Long idRevenda){
        if(idRevenda == 0)
            return softDeleteRevendaUsuarioPorUsuario(usuario);

        Revenda revenda = revendaService.obterPorId(idRevenda);
        RevendaUsuario revendaUsuario = revendaUsuarioRepository.findByRevendaAndUsuario(revenda, usuario);
        revendaUsuario.setActive(false);
        revendaUsuario.setUpdatedAt(LocalDateTime.now());
        revendaUsuarioRepository.save(revendaUsuario);

        return List.of(revendaUsuario);

    }

    public void deleteRevendaUsuario(Usuario usuario, Long idRevenda){
        if(idRevenda == 0) {
            revendaUsuarioRepository.deleteAllByUsuario(usuario);
            return;
        }

        Revenda revenda = revendaService.obterPorId(idRevenda);
        RevendaUsuario revendaUsuario = revendaUsuarioRepository.findByRevendaAndUsuario(revenda, usuario);
        revendaUsuarioRepository.delete(revendaUsuario);
    }

    public RevendaUsuario checkUsuarioRequisitos(Long idUsuario, Long idRevenda){
        Optional<RevendaUsuario> revUser = revendaUsuarioRepository.findRevendaUsuarioByIdUsuarioAndIdRevendaAndRole(idUsuario, idRevenda, RoleEnum.ASSISTENTE.name());
        return revUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private List<RevendaUsuario> softDeleteRevendaUsuarioPorUsuario(Usuario usuario){
        List<RevendaUsuario> revendasUser = revendaUsuarioRepository.findAllByUsuario(usuario);
        revendasUser.forEach(revendaUsuario -> {
            revendaUsuario.setActive(false);
            revendaUsuario.setUpdatedAt(LocalDateTime.now());
        });

        return revendaUsuarioRepository.saveAll(revendasUser);
    }

}
