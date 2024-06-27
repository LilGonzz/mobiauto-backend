package com.LGNZZ.mobiauto_backend_interview.repository;

import com.LGNZZ.mobiauto_backend_interview.entity.Revenda;
import com.LGNZZ.mobiauto_backend_interview.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RevendaRepository extends JpaRepository<Revenda, Long> {

    List<Revenda> findAll();

    @Query("SELECT rev FROM Revenda rev WHERE rev.isActive = true")
    List<Revenda> findAllRevendasAtivas();
    Optional<Revenda> findByCnpj(String cnpj);
}
