package com.LGNZZ.mobiauto_backend_interview.entity;

import com.LGNZZ.mobiauto_backend_interview.entity.Enums.SituacaoOportunidadeEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "OPORTUNIDADE")
public class Oportunidade extends BaseClass{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_OPORTUNIDADE")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "CD_SITUACAO", length = 15, nullable = false)
    private SituacaoOportunidadeEnum situacao;

    @Column(name = "DS_CONCLUSAO")
    private String descricaoConclusao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_REVENDA", foreignKey = @ForeignKey(name = "REV_OP_FK"), nullable = false)
    private Revenda revenda;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CLIENTE", foreignKey = @ForeignKey(name = "CLI_OP_FK"), nullable = false)
    private Cliente cliente;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_VEICULO", foreignKey = @ForeignKey(name = "VEI_OP_FK"), nullable = false)
    private Veiculo veiculo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SituacaoOportunidadeEnum getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoOportunidadeEnum situacao) {
        this.situacao = situacao;
    }

    public String getDescricaoConclusao() {
        return descricaoConclusao;
    }

    public void setDescricaoConclusao(String descricaoConclusao) {
        this.descricaoConclusao = descricaoConclusao;
    }

    public Revenda getRevenda() {
        return revenda;
    }

    public void setRevenda(Revenda revenda) {
        this.revenda = revenda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
}
