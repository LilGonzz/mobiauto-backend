package com.LGNZZ.mobiauto_backend_interview.entity;

import com.LGNZZ.mobiauto_backend_interview.entity.Enums.RoleEnum;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "REVENDA_USUARIO")
public class RevendaUsuario extends BaseClass{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REVENDA_USUARIO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_USUARIO", foreignKey = @ForeignKey(name = "USER_REVUS_FK"))
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_REVENDA", foreignKey = @ForeignKey(name = "REV_REVUS_FK"))
    private Revenda revenda;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Column(name = "is_active")
    private Boolean isActive;

    public RevendaUsuario() {}
    public RevendaUsuario(Usuario usuario, Revenda revenda, RoleEnum role) {
        this.usuario = usuario;
        this.revenda = revenda;
        this.role = role;
        this.isActive = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RevendaUsuario that = (RevendaUsuario) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Revenda getRevenda() {
        return revenda;
    }

    public void setRevenda(Revenda revenda) {
        this.revenda = revenda;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum name) {
        this.role = name;
    }
}
