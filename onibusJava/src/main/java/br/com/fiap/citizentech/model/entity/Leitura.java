package br.com.fiap.citizentech.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity(name = "Leitura")
@Table(name = "dddj_leitura")
public class Leitura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_leitura")
    private Long idLeitura;

    @ManyToOne
    @JoinColumn(name = "id_camera")
    private Camera camera;

    @ManyToOne
    @JoinColumn(name = "id_operacao")
    private Operacao operacao;

    @Column(name = "data_hora_leitura")
    private LocalDateTime dataHoraLeitura;

    @Column(name = "contagem_pessoas")
    private Integer contagemPessoas;

    @Column(name = "nivel_confianca")
    private Double nivelConfianca;

    @Column(name = "status_leitura", length = 10)
    private String statusLeitura;

    public Leitura() {
    }

    public Leitura(Camera camera, Operacao operacao, LocalDateTime dataHoraLeitura,
                   Integer contagemPessoas, Double nivelConfianca, String statusLeitura) {
        this.camera = camera;
        this.operacao = operacao;
        this.dataHoraLeitura = dataHoraLeitura;
        this.contagemPessoas = contagemPessoas;
        this.nivelConfianca = nivelConfianca;
        this.statusLeitura = statusLeitura;
    }

    public Long getIdLeitura() {
        return idLeitura;
    }

    public void setIdLeitura(Long idLeitura) {
        this.idLeitura = idLeitura;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Operacao getOperacao() {
        return operacao;
    }

    public void setOperacao(Operacao operacao) {
        this.operacao = operacao;
    }

    public LocalDateTime getDataHoraLeitura() {
        return dataHoraLeitura;
    }

    public void setDataHoraLeitura(LocalDateTime dataHoraLeitura) {
        this.dataHoraLeitura = dataHoraLeitura;
    }

    public Integer getContagemPessoas() {
        return contagemPessoas;
    }

    public void setContagemPessoas(Integer contagemPessoas) {
        this.contagemPessoas = contagemPessoas;
    }

    public Double getNivelConfianca() {
        return nivelConfianca;
    }

    public void setNivelConfianca(Double nivelConfianca) {
        this.nivelConfianca = nivelConfianca;
    }

    public String getStatusLeitura() {
        return statusLeitura;
    }

    public void setStatusLeitura(String statusLeitura) {
        this.statusLeitura = statusLeitura;
    }
}