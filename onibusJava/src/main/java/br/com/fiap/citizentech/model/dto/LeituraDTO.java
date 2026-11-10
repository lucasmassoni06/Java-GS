package br.com.fiap.citizentech.model.dto;

import br.com.fiap.citizentech.model.entity.Camera;
import br.com.fiap.citizentech.model.entity.FaixaOcupacao;
import br.com.fiap.citizentech.model.entity.Leitura;
import br.com.fiap.citizentech.model.entity.Operacao;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class LeituraDTO {

    private Long idLeitura;

    @NotNull(message = "A camera e obrigatoria")
    private Long idCamera;

    @NotNull(message = "A operacao e obrigatoria")
    private Long idOperacao;

    @NotNull(message = "A data e hora da leitura e obrigatoria")
    private LocalDateTime dataHoraLeitura;

    @NotNull(message = "A contagem de pessoas e obrigatoria")
    @Min(value = 0, message = "A contagem nao pode ser negativa")
    private Integer contagemPessoas;

    @NotNull(message = "O nivel de confianca e obrigatorio")
    @DecimalMin(value = "0.0", message = "O nivel minimo e 0")
    @DecimalMax(value = "100.0", message = "O nivel maximo e 100")
    private Double nivelConfianca;

    private String statusLeitura;

    private String nomeCamera;
    private String nomeLinha;
    private String prefixoFrota;

    public LeituraDTO() {
    }

    public LeituraDTO(Leitura leitura) {
        this.idLeitura = leitura.getIdLeitura();
        this.idCamera = leitura.getCamera() != null ? leitura.getCamera().getIdCamera() : null;
        this.idOperacao = leitura.getOperacao() != null ? leitura.getOperacao().getIdOperacao() : null;
        this.dataHoraLeitura = leitura.getDataHoraLeitura();
        this.contagemPessoas = leitura.getContagemPessoas();
        this.nivelConfianca = leitura.getNivelConfianca();
        this.statusLeitura = leitura.getStatusLeitura();
        this.nomeCamera = leitura.getCamera() != null ? leitura.getCamera().getNomeCamera() : null;
        this.nomeLinha = leitura.getOperacao() != null && leitura.getOperacao().getLinha() != null
                ? leitura.getOperacao().getLinha().getNomeLinha() : null;
        this.prefixoFrota = leitura.getOperacao() != null && leitura.getOperacao().getVeiculo() != null
                ? leitura.getOperacao().getVeiculo().getPrefixoFrota() : null;
    }

    public Leitura toEntity(Camera camera, Operacao operacao) {
        Leitura leitura = new Leitura();
        leitura.setIdLeitura(this.idLeitura);
        leitura.setCamera(camera);
        leitura.setOperacao(operacao);
        leitura.setDataHoraLeitura(this.dataHoraLeitura);
        leitura.setContagemPessoas(this.contagemPessoas);
        leitura.setNivelConfianca(this.nivelConfianca);
        leitura.setStatusLeitura(this.statusLeitura);
        return leitura;
    }

    public static Double calcularPercentual(Integer contagem, Integer capacidadeTotal) {
        if (contagem == null || capacidadeTotal == null || capacidadeTotal == 0) {
            return 0.0;
        }
        return (contagem * 100.0) / capacidadeTotal;
    }

    public static FaixaOcupacao escolherFaixa(Double percentual, List<FaixaOcupacao> faixas) {
        if (faixas == null || faixas.isEmpty() || percentual == null) {
            return null;
        }
        return faixas.stream()
                .filter(f -> percentual >= f.getPercentualMin() && percentual <= f.getPercentualMax())
                .findFirst()
                .orElse(faixas.get(faixas.size() - 1));
    }

    public Long getIdLeitura() {
        return idLeitura;
    }

    public void setIdLeitura(Long idLeitura) {
        this.idLeitura = idLeitura;
    }

    public Long getIdCamera() {
        return idCamera;
    }

    public void setIdCamera(Long idCamera) {
        this.idCamera = idCamera;
    }

    public Long getIdOperacao() {
        return idOperacao;
    }

    public void setIdOperacao(Long idOperacao) {
        this.idOperacao = idOperacao;
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

    public String getNomeCamera() {
        return nomeCamera;
    }

    public void setNomeCamera(String nomeCamera) {
        this.nomeCamera = nomeCamera;
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
}