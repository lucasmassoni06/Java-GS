package br.com.fiap.citizentech.model.dto;

import br.com.fiap.citizentech.model.entity.FaixaOcupacao;
import br.com.fiap.citizentech.model.entity.Operacao;
import br.com.fiap.citizentech.model.entity.StatusOcupacao;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class StatusOcupacaoDTO {

    private Long idStatus;
    private Long idOperacao;
    private Long idFaixa;
    private String nomeFaixa;
    private String corHex;
    private String nomeLinha;
    private String prefixoFrota;
    private LocalDateTime dataHoraReferencia;
    private Double percentualOcupacao;
    private LocalDateTime atualizadoEm;

    public StatusOcupacaoDTO() {
    }

    public StatusOcupacaoDTO(StatusOcupacao status) {
        this.idStatus = status.getIdStatus();
        this.idOperacao = status.getOperacao() != null ? status.getOperacao().getIdOperacao() : null;
        this.idFaixa = status.getFaixa() != null ? status.getFaixa().getIdFaixa() : null;
        this.nomeFaixa = status.getFaixa() != null ? status.getFaixa().getNomeFaixa() : null;
        this.corHex = status.getFaixa() != null ? status.getFaixa().getCorHex() : null;
        this.nomeLinha = status.getOperacao() != null && status.getOperacao().getLinha() != null
                ? status.getOperacao().getLinha().getNomeLinha() : null;
        this.prefixoFrota = status.getOperacao() != null && status.getOperacao().getVeiculo() != null
                ? status.getOperacao().getVeiculo().getPrefixoFrota() : null;
        this.dataHoraReferencia = status.getDataHoraReferencia();
        this.percentualOcupacao = status.getPercentualOcupacao();
        this.atualizadoEm = status.getAtualizadoEm();
    }

    public StatusOcupacao toEntity(Operacao operacao, FaixaOcupacao faixa) {
        StatusOcupacao status = new StatusOcupacao();
        status.setIdStatus(this.idStatus);
        status.setOperacao(operacao);
        status.setFaixa(faixa);
        status.setDataHoraReferencia(this.dataHoraReferencia);
        status.setPercentualOcupacao(this.percentualOcupacao);
        status.setAtualizadoEm(this.atualizadoEm);
        return status;
    }

    public Long getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Long idStatus) {
        this.idStatus = idStatus;
    }

    public Long getIdOperacao() {
        return idOperacao;
    }

    public void setIdOperacao(Long idOperacao) {
        this.idOperacao = idOperacao;
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

    public String getCorHex() {
        return corHex;
    }

    public void setCorHex(String corHex) {
        this.corHex = corHex;
    }

    public String getNomeLinha() {
        return nomeLinha;
    }

    public void setNomeLinha(String nomeLinha) {
        this.nomeLinha = nomeLinha;
    }

    public String getPrefixoFrota() {
        return prefixoFrota;
    }

    public void setPrefixoFrota(String prefixoFrota) {
        this.prefixoFrota = prefixoFrota;
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

    @NotNull(message = "A data e hora de referencia e obrigatoria")
    private LocalDateTime dataHoraReferencia;
}