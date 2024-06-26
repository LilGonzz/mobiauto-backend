package com.LGNZZ.mobiauto_backend_interview.repository;

import com.LGNZZ.mobiauto_backend_interview.entity.Atendimento;
import com.LGNZZ.mobiauto_backend_interview.entity.logs.AtendimentoLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtendimentoLogRepository extends JpaRepository<AtendimentoLog, Long> {

    @Query("SELECT log FROM AtendimentoLog log WHERE log.atendimento.id = :idAtendimento")
    Optional<List<AtendimentoLog>> findAllByIdAtendimento(Long idAtendimento);
}
