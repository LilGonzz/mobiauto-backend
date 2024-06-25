package com.LGNZZ.mobiauto_backend_interview.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ATENDIMENTO")
public class Atendimento extends BaseClass{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ATENDIMENTO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_OPORTUNIDADE", foreignKey = @ForeignKey(name = "OP_ATEND_FK"), nullable = false)
    private Oportunidade oportunidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_REVENDA_USUARIO", foreignKey = @ForeignKey(name = "REVUSER_ATEND_FK"), nullable = false)
    private RevendaUsuario revendaUsuario;


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

    public RevendaUsuario getRevendaUsuario() {
        return revendaUsuario;
    }

    public void setRevendaUsuario(RevendaUsuario revendaUsuario) {
        this.revendaUsuario = revendaUsuario;
    }
}
