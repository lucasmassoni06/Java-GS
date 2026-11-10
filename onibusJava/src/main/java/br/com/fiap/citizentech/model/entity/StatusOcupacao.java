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

@Entity(name = "StatusOcupacao")
@Table(name = "dddj_status_ocupacao")
public class StatusOcupacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status")
    private Long idStatus;

    @ManyToOne
    @JoinColumn(name = "id_operacao")
    private Operacao operacao;

    @ManyToOne
    @JoinColumn(name = "id_faixa")
    private FaixaOcupacao faixa;

    @Column(name = "data_hora_referencia")
    private LocalDateTime dataHoraReferencia;

    @Column(name = "percentual_ocupacao")
    private Double percentualOcupacao;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    public StatusOcupacao() {
    }

    public StatusOcupacao(Operacao operacao, FaixaOcupacao faixa, LocalDateTime dataHoraReferencia,
                          Double percentualOcupacao, LocalDateTime atualizadoEm) {
        this.operacao = operacao;
        this.faixa = faixa;
        this.dataHoraReferencia = dataHoraReferencia;
        this.percentualOcupacao = percentualOcupacao;
        this.atualizadoEm = atualizadoEm;
    }

    public Long getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Long idStatus) {
        this.idStatus = idStatus;
    }

    public Operacao getOperacao() {
        return operacao;
    }

    public void setOperacao(Operacao operacao) {
        this.operacao = operacao;
    }

    public FaixaOcupacao getFaixa() {
        return faixa;
    }

    public void setFaixa(FaixaOcupacao faixa) {
        this.faixa = faixa;
    }

    public LocalDateTime getDataHoraReferencia() {
        return dataHoraReferencia;
    }

    public void setDataHoraReferencia(LocalDateTime dataHoraReferencia) {
        this.dataHoraReferencia = dataHoraReferencia;
    }

    public Double getPercentualOcupacao() {
        return percentualOcupacao;
    }

    public void setPercentualOcupacao(Double percentualOcupacao) {
        this.percentualOcupacao = percentualOcupacao;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}