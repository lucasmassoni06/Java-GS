package br.com.fiap.citizentech.model.dto;

import br.com.fiap.citizentech.model.entity.Linha;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LinhaDTO {

    private Long idLinha;

    @NotBlank(message = "O codigo da linha e obrigatorio")
    @Size(max = 20, message = "O codigo deve ter no maximo 20 caracteres")
    private String codigoLinha;

    @NotBlank(message = "O nome da linha e obrigatorio")
    @Size(max = 100, message = "O nome deve ter no maximo 100 caracteres")
    private String nomeLinha;

    @NotBlank(message = "A origem e obrigatoria")
    @Size(max = 100, message = "A origem deve ter no maximo 100 caracteres")
    private String origem;

    @NotBlank(message = "O destino e obrigatorio")
    @Size(max = 100, message = "O destino deve ter no maximo 100 caracteres")
    private String destino;

    @NotBlank(message = "O campo ativa e obrigatorio")
    private String ativa;

    public LinhaDTO() {
    }

    public LinhaDTO(Linha linha) {
        this.idLinha = linha.getIdLinha();
        this.codigoLinha = linha.getCodigoLinha();
        this.nomeLinha = linha.getNomeLinha();
        this.origem = linha.getOrigem();
        this.destino = linha.getDestino();
        this.ativa = linha.getAtiva();
    }

    public Linha toEntity() {
        Linha linha = new Linha();
        linha.setIdLinha(this.idLinha);
        linha.setCodigoLinha(this.codigoLinha);
        linha.setNomeLinha(this.nomeLinha);
        linha.setOrigem(this.origem);
        linha.setDestino(this.destino);
        linha.setAtiva(this.ativa != null ? this.ativa : "S");
        return linha;
    }

    public Long getIdLinha() {
        return idLinha;
    }

    public void setIdLinha(Long idLinha) {
        this.idLinha = idLinha;
    }

    public String getCodigoLinha() {
        return codigoLinha;
    }

    public void setCodigoLinha(String codigoLinha) {
        this.codigoLinha = codigoLinha;
    }

    public String getNomeLinha() {
        return nomeLinha;
    }

    public void setNomeLinha(String nomeLinha) {
        this.nomeLinha = nomeLinha;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getAtiva() {
        return ativa;
    }

    public void setAtiva(String ativa) {
        this.ativa = ativa;
    }
}