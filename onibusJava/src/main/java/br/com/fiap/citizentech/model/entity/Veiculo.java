package br.com.fiap.citizentech.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity(name = "Veiculo")
@Table(name = "dddj_veiculo")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_veiculo")
    private Long idVeiculo;

    @Column(name = "prefixo_frota", length = 20)
    private String prefixoFrota;

    @Column(name = "modelo", length = 60)
    private String modelo;

    @Column(name = "capacidade_sentados")
    private Integer capacidadeSentados;

    @Column(name = "capacidade_em_pe")
    private Integer capacidadeEmPe;

    @Column(name = "capacidade_total")
    private Integer capacidadeTotal;

    @Column(name = "articulado", length = 1)
    private String articulado;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    public Veiculo() {
    }

    public Long getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Long idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public String getPrefixoFrota() {
        return prefixoFrota;
    }

    public void setPrefixoFrota(String prefixoFrota) {
        this.prefixoFrota = prefixoFrota;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getCapacidadeSentados() {
        return capacidadeSentados;
    }

    public void setCapacidadeSentados(Integer capacidadeSentados) {
        this.capacidadeSentados = capacidadeSentados;
    }

    public Integer getCapacidadeEmPe() {
        return capacidadeEmPe;
    }

    public void setCapacidadeEmPe(Integer capacidadeEmPe) {
        this.capacidadeEmPe = capacidadeEmPe;
    }

    public Integer getCapacidadeTotal() {
        return capacidadeTotal;
    }

    public void setCapacidadeTotal(Integer capacidadeTotal) {
        this.capacidadeTotal = capacidadeTotal;
    }

    public String getArticulado() {
        return articulado;
    }

    public void setArticulado(String articulado) {
        this.articulado = articulado;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}