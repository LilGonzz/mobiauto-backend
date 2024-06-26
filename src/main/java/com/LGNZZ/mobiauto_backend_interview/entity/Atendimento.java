package com.LGNZZ.mobiauto_backend_interview.entity;

import com.LGNZZ.mobiauto_backend_interview.entity.logs.AtendimentoLog;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ATENDIMENTO")
public class Atendimento extends BaseClass{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ATENDIMENTO")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_OPORTUNIDADE", foreignKey = @ForeignKey(name = "OP_ATEND_FK"), nullable = false)
    private Oportunidade oportunidade;

    @ManyToOne
    @JoinColumn(name = "ID_REVENDA", foreignKey = @ForeignKey(name = "REV_ATEND_FK"), nullable = false)
    private Revenda revenda;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", foreignKey = @ForeignKey(name = "USER_ATEND_FK"), nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "atendimento", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AtendimentoLog> logs;

    public Atendimento() {}

    public Atendimento(Oportunidade oportunidade, Revenda revenda, Usuario usuario) {
        super();
        this.oportunidade = oportunidade;
        this.revenda = revenda;
        this.usuario = usuario;
        logs = new ArrayList<>();
    }

    public void addLog(AtendimentoLog log) {
        logs.add(log);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Oportunidade getOportunidade() {
        return oportunidade;
    }

    public void setOportunidade(Oportunidade oportunidade) {
        this.oportunidade = oportunidade;
    }

    public List<AtendimentoLog> getLogs() {
        return logs;
    }

    public void setLogs(List<AtendimentoLog> logs) {
        this.logs = logs;
    }

    public Revenda getRevenda() {
        return revenda;
    }

    public void setRevenda(Revenda revenda) {
        this.revenda = revenda;
    }

    public com.LGNZZ.mobiauto_backend_interview.entity.Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(com.LGNZZ.mobiauto_backend_interview.entity.Usuario usuario) {
        usuario = usuario;
    }
}
