package br.com.fiap.citizentech.model.dto;

import br.com.fiap.citizentech.model.entity.FaixaOcupacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FaixaOcupacaoDTO {

    private Long idFaixa;

    @NotBlank(message = "O nome da faixa e obrigatorio")
    @Size(max = 20, message = "O nome deve ter no maximo 20 caracteres")
    private String nomeFaixa;

    @NotNull(message = "O percentual minimo e obrigatorio")
    private Double percentualMin;

    @NotNull(message = "O percentual maximo e obrigatorio")
    private Double percentualMax;

    @Size(max = 7, message = "A cor deve ter no maximo 7 caracteres")
    private String corHex;

    public FaixaOcupacaoDTO() {
    }

    public FaixaOcupacaoDTO(FaixaOcupacao faixa) {
        this.idFaixa = faixa.getIdFaixa();
        this.nomeFaixa = faixa.getNomeFaixa();
        this.percentualMin = faixa.getPercentualMin();
        this.percentualMax = faixa.getPercentualMax();
        this.corHex = faixa.getCorHex();
    }

    public FaixaOcupacao toEntity() {
        FaixaOcupacao faixa = new FaixaOcupacao();
        faixa.setIdFaixa(this.idFaixa);
        faixa.setNomeFaixa(this.nomeFaixa);
        faixa.setPercentualMin(this.percentualMin);
        faixa.setPercentualMax(this.percentualMax);
        faixa.setCorHex(this.corHex);
        return faixa;
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