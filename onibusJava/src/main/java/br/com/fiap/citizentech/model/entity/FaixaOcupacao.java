package br.com.fiap.citizentech.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "FaixaOcupacao")
@Table(name = "dddj_faixa_ocupacao")
public class FaixaOcupacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_faixa")
    private Long idFaixa;

    @Column(name = "nome_faixa", length = 30)
    private String nomeFaixa;

    @Column(name = "percentual_min")
    private Double percentualMin;

    @Column(name = "percentual_max")
    private Double percentualMax;

    @Column(name = "cor_hex", length = 10)
    private String corHex;

    public FaixaOcupacao() {
    }

    public Long getIdFaixa() {
        return idFaixa;
    }

    public void setIdFaixa(Long idFaixa) {
        this.idFaixa = idFaixa;
    }

    public String getNomeFaixa() {
        return nomeFaixa;
    }

    public void setNomeFaixa(String nomeFaixa) {
        this.nomeFaixa = nomeFaixa;
    }

    public Double getPercentualMin() {
        return percentualMin;
    }

    public void setPercentualMin(Double percentualMin) {
        this.percentualMin = percentualMin;
    }

    public Double getPercentualMax() {
        return percentualMax;
    }

    public void setPercentualMax(Double percentualMax) {
        this.percentualMax = percentualMax;
    }

    public String getCorHex() {
        return corHex;
    }

    public void setCorHex(String corHex) {
        this.corHex = corHex;
    }
}