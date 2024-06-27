package com.LGNZZ.mobiauto_backend_interview.repository;

import com.LGNZZ.mobiauto_backend_interview.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    @Query("SELECT user FROM Usuario user LEFT JOIN FETCH user.rolesPorRevenda revUser WHERE revUser.revenda.id = :idRevenda AND revUser.role = 'ASSISTENTE'")
    List<Usuario> obterUsuarioPorRevenda(Long idRevenda);
}
