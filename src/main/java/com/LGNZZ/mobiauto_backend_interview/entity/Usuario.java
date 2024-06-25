package com.LGNZZ.mobiauto_backend_interview.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "USUARIO", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class Usuario extends BaseClass{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long id;

    @Column(name = "DS_NOME", nullable = false)
    private String name;

    @Column(name = "DS_EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "DS_SENHA", nullable = false)
    private String senha;

    @OneToMany(mappedBy = "usuario")
    private Set<RevendaUsuario> rolesPorRevenda;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<RevendaUsuario> getRolesPorRevenda() {
        return rolesPorRevenda;
    }

    public void setRolesPorRevenda(Set<RevendaUsuario> rolesPorRevenda) {
        this.rolesPorRevenda = rolesPorRevenda;
    }
}
