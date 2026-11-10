package br.com.fiap.citizentech.model.dto;

import br.com.fiap.citizentech.model.entity.Veiculo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class VeiculoDTO {

    private Long idVeiculo;

    @NotBlank(message = "O prefixo da frota e obrigatorio")
    @Size(max = 20, message = "O prefixo deve ter no maximo 20 caracteres")
    private String prefixoFrota;

    @NotBlank(message = "O modelo e obrigatorio")
    @Size(max = 60, message = "O modelo deve ter no maximo 60 caracteres")
    private String modelo;

    @Min(value = 0, message = "A capacidade de sentados nao pode ser negativa")
    private Integer capacidadeSentados;

    @Min(value = 0, message = "A capacidade em pe nao pode ser negativa")
    private Integer capacidadeEmPe;

    @Min(value = 0, message = "A capacidade total nao pode ser negativa")
    private Integer capacidadeTotal;

    private String articulado;

    private LocalDate dataCriacao;

    public VeiculoDTO() {
    }

    public VeiculoDTO(Veiculo veiculo) {
        this.idVeiculo = veiculo.getIdVeiculo();
        this.prefixoFrota = veiculo.getPrefixoFrota();
        this.modelo = veiculo.getModelo();
        this.capacidadeSentados = veiculo.getCapacidadeSentados();
        this.capacidadeEmPe = veiculo.getCapacidadeEmPe();
        this.capacidadeTotal = veiculo.getCapacidadeTotal();
        this.articulado = veiculo.getArticulado();
        this.dataCriacao = veiculo.getDataCriacao();
    }

    public Veiculo toEntity() {
        Veiculo veiculo = new Veiculo();
        veiculo.setIdVeiculo(this.idVeiculo);
        veiculo.setPrefixoFrota(this.prefixoFrota);
        veiculo.setModelo(this.modelo);
        veiculo.setCapacidadeSentados(this.capacidadeSentados);
        veiculo.setCapacidadeEmPe(this.capacidadeEmPe);
        veiculo.setCapacidadeTotal(this.capacidadeTotal);
        veiculo.setArticulado(this.articulado);
        veiculo.setDataCriacao(this.dataCriacao);
        return veiculo;
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