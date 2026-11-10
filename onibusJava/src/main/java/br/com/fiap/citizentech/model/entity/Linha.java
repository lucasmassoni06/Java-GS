package br.com.fiap.citizentech.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "Linha")
@Table(name = "dddj_linha")
public class Linha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_linha")
    private Long idLinha;

    @Column(name = "codigo_linha", length = 10)
    private String codigoLinha;

    @Column(name = "nome_linha", length = 100)
    private String nomeLinha;

    @Column(name = "origem", length = 80)
    private String origem;

    @Column(name = "destino", length = 80)
    private String destino;

    @Column(name = "ativa", length = 1)
    private String ativa;

    public Linha() {
    }

    public Linha(String codigoLinha, String nomeLinha, String origem, String destino, String ativa) {
        this.codigoLinha = codigoLinha;
        this.nomeLinha = nomeLinha;
        this.origem = origem;
        this.destino = destino;
        this.ativa = ativa;
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