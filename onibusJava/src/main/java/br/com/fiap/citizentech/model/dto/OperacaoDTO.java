package br.com.fiap.citizentech.model.dto;

import br.com.fiap.citizentech.model.entity.Linha;
import br.com.fiap.citizentech.model.entity.Operacao;
import br.com.fiap.citizentech.model.entity.Veiculo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OperacaoDTO {

    private Long idOperacao;

    @NotNull(message = "O veiculo e obrigatorio")
    private Long idVeiculo;

    @NotNull(message = "A linha e obrigatoria")
    private Long idLinha;

    @NotNull(message = "A data da operacao e obrigatoria")
    private LocalDate dataOperacao;

    private LocalDateTime horaInicio;

    private LocalDateTime horaFim;

    @Size(max = 10, message = "O sentido deve ter no maximo 10 caracteres")
    private String sentido;

    public OperacaoDTO() {
    }

    public OperacaoDTO(Operacao operacao) {
        this.idOperacao = operacao.getIdOperacao();
        this.idVeiculo = operacao.getVeiculo() != null ? operacao.getVeiculo().getIdVeiculo() : null;
        this.idLinha = operacao.getLinha() != null ? operacao.getLinha().getIdLinha() : null;
        this.dataOperacao = operacao.getDataOperacao();
        this.horaInicio = operacao.getHoraInicio();
        this.horaFim = operacao.getHoraFim();
        this.sentido = operacao.getSentido();
    }

    public Operacao toEntity(Veiculo veiculo, Linha linha) {
        Operacao operacao = new Operacao();
        operacao.setIdOperacao(this.idOperacao);
        operacao.setVeiculo(veiculo);
        operacao.setLinha(linha);
        operacao.setDataOperacao(this.dataOperacao);
        operacao.setHoraInicio(this.horaInicio);
        operacao.setHoraFim(this.horaFim);
        operacao.setSentido(this.sentido);
        return operacao;
    }

    public Long getIdOperacao() {
        return idOperacao;
    }

    public void setIdOperacao(Long idOperacao) {
        this.idOperacao = idOperacao;
    }

    public Long getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Long idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public Long getIdLinha() {
        return idLinha;
    }

    public void setIdLinha(Long idLinha) {
        this.idLinha = idLinha;
    }

    public LocalDate getDataOperacao() {
        return dataOperacao;
    }

    public void setDataOperacao(LocalDate dataOperacao) {
        this.dataOperacao = dataOperacao;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDateTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalDateTime horaFim) {
        this.horaFim = horaFim;
    }

    public String getSentido() {
        return sentido;
    }

    public void setSentido(String sentido) {
        this.sentido = sentido;
    }
}