package com.LGNZZ.mobiauto_backend_interview.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "VEICULO")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VEICULO")
    private Long id;

    @Column(name = "DS_MARCA", nullable = false)
    private String marca;

    @Column(name = "DS_VERSAO", nullable = false)
    private String versao;

    @Column(name = "DS_MODELO", nullable = false)
    private String modelo;

    @Column(name = "DS_ANO_MODELO", nullable = false)
    private String anoModelo;
    public Veiculo() {}
    public Veiculo(String marca, String versao, String modelo, String anoModelo) {
        this.marca = marca;
        this.versao = versao;
        this.modelo = modelo;
        this.anoModelo = anoModelo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAnoModelo() {
        return anoModelo;
    }

    public void setAnoModelo(String anoModelo) {
        this.anoModelo = anoModelo;
    }
}
