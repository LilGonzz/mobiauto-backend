package com.LGNZZ.mobiauto_backend_interview.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "REVENDA", uniqueConstraints={@UniqueConstraint(columnNames={"DS_CNPJ"})})
public class Revenda extends BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REVENDA")
    private Long id;

    @Column(name = "DS_CNPJ", nullable = false, length = 14, unique = true)
    private String cnpj;

    @Column(name = "DS_NOME_SOCIAL", nullable = false)
    private String nomeSocial;

    @OneToMany(mappedBy = "revenda", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RevendaUsuario> revendaUsuarios;

    public Revenda(){}
    public Revenda(String cnpj, String nomeSocial) {
        super();
        this.cnpj = cnpj;
        this.nomeSocial = nomeSocial;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    public Set<RevendaUsuario> getRevendaUsuarios() {
        return revendaUsuarios;
    }

    public void setRevendaUsuarios(Set<RevendaUsuario> revendaUsuarios) {
        this.revendaUsuarios = revendaUsuarios;
    }
}
