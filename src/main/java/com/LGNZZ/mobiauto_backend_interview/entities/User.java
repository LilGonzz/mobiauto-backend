package com.LGNZZ.mobiauto_backend_interview.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "USUARIO")
public class User extends BaseClass{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long id;

    @Column(name = "DS_NOME", nullable = false)
    private String name;

    @Column(name = "DS_EMAIL", nullable = false)
    private String email;

    @Column(name = "DS_SENHA", nullable = false)
    private String senha;

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
}
