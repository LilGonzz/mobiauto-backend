package com.LGNZZ.mobiauto_backend_interview.entity.logs;


import com.LGNZZ.mobiauto_backend_interview.entity.Atendimento;
import com.LGNZZ.mobiauto_backend_interview.entity.Enums.TipoAcaoEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ATENDIMENTO_LOG")
public class AtendimentoLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ATENDIMENTO_LOG")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ATENDIMENTO", foreignKey = @ForeignKey(name = "ATEND_ATENDLOG_FK"), nullable = false)
    private Atendimento atendimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "CD_TIPO_ACAO", length = 25, nullable = false)
    private TipoAcaoEnum tipoAcao;

    @Column(name = "DT_ACAO", nullable = false)
    private LocalDateTime dataAcao;

    public AtendimentoLog() {}
    public AtendimentoLog(Atendimento atendimento, TipoAcaoEnum tipoAcao) {
        this.atendimento = atendimento;
        this.tipoAcao = tipoAcao;
        this.dataAcao = LocalDateTime.now();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }

    public TipoAcaoEnum getTipoAcao() {
        return tipoAcao;
    }

    public void setTipoAcao(TipoAcaoEnum tipoAcao) {
        this.tipoAcao = tipoAcao;
    }

    public LocalDateTime getDataAcao() {
        return dataAcao;
    }

    public void setDataAcao(LocalDateTime dataAcao) {
        this.dataAcao = dataAcao;
    }
}
