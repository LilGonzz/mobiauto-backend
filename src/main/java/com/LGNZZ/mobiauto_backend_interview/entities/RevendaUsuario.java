package com.LGNZZ.mobiauto_backend_interview.entities;

import com.LGNZZ.mobiauto_backend_interview.entities.Enums.RoleEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "REVENDA_USUARIO")
public class RevendaUsuario extends BaseClass{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REVENDA_USUARIO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", foreignKey = @ForeignKey(name = "USER_REVUS_FK"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_REVENDA", foreignKey = @ForeignKey(name = "REV_REVUS_FK"))
    private Revenda revenda;

    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUsuario() {
        return user;
    }

    public void setUsuario(User user) {
        this.user = user;
    }

    public Revenda getRevenda() {
        return revenda;
    }

    public void setRevenda(Revenda revenda) {
        this.revenda = revenda;
    }

    public RoleEnum getName() {
        return name;
    }

    public void setName(RoleEnum name) {
        this.name = name;
    }
}
