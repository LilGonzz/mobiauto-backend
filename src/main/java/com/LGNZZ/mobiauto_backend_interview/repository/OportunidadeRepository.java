package com.LGNZZ.mobiauto_backend_interview.repository;

import com.LGNZZ.mobiauto_backend_interview.entity.Oportunidade;
import com.LGNZZ.mobiauto_backend_interview.entity.Revenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OportunidadeRepository extends JpaRepository<Oportunidade, Long> {

    @Query("SELECT op FROM Oportunidade op WHERE op.revenda.id = :idRevenda")
    Optional<List<Oportunidade>> findAllByIdRevenda(Long idRevenda);
}
