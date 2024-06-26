package com.LGNZZ.mobiauto_backend_interview.repository;

import com.LGNZZ.mobiauto_backend_interview.entity.Revenda;
import com.LGNZZ.mobiauto_backend_interview.entity.RevendaUsuario;
import com.LGNZZ.mobiauto_backend_interview.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevendaUsuarioRepository extends JpaRepository<RevendaUsuario, Long> {

    List<RevendaUsuario> findAllByUsuario(Usuario usuario);
    List<RevendaUsuario> findAllByRevenda(Revenda revenda);
    RevendaUsuario findByRevendaAndUsuario(Revenda revenda, Usuario usuario);
    void deleteAllByUsuario(Usuario usuario);
}
