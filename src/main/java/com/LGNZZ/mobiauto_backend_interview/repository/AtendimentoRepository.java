package com.LGNZZ.mobiauto_backend_interview.repository;

import com.LGNZZ.mobiauto_backend_interview.entity.Atendimento;
import com.LGNZZ.mobiauto_backend_interview.entity.Oportunidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    @Query("SELECT at FROM Atendimento at WHERE at.oportunidade = :idOportunidade")
    Optional<List<Atendimento>> findAllByIdOportunidade(Long idOportunidade);

    @Query("SELECT at FROM Atendimento at WHERE at.usuario IS NULL")
    List<Atendimento> obterAtendimentosSemResponsavel();
    @Query("SELECT at FROM Atendimento at WHERE at.usuario = :idUsuario AND at.revenda = :idRevenda")
    List<Atendimento> obterQuantidadeAtendimentoPorUsuarioAndRevenda(Long idUsuario, Long idRevenda);
}
