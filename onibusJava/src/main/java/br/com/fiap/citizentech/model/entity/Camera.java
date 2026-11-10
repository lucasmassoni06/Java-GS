package br.com.fiap.citizentech.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity(name = "Camera")
@Table(name = "dddj_camera")
public class Camera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_camera")
    private Long idCamera;

    @ManyToOne
    @JoinColumn(name = "id_veiculo")
    private Veiculo veiculo;

    @Column(name = "nome_camera", length = 60)
    private String nomeCamera;

    @Column(name = "posicao", length = 60)
    private String posicao;

    @Column(name = "resolucao", length = 30)
    private String resolucao;

    @Column(name = "data_instalacao")
    private LocalDate dataInstalacao;

    @Column(name = "status_camera", length = 20)
    private String statusCamera;

    public Camera() {
    }

    public Long getIdCamera() {
        return idCamera;
    }

    public void setIdCamera(Long idCamera) {
        this.idCamera = idCamera;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public String getNomeCamera() {
        return nomeCamera;
    }

    public void setNomeCamera(String nomeCamera) {
        this.nomeCamera = nomeCamera;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public String getResolucao() {
        return resolucao;
    }

    public void setResolucao(String resolucao) {
        this.resolucao = resolucao;
    }

    public LocalDate getDataInstalacao() {
        return dataInstalacao;
    }

    public void setDataInstalacao(LocalDate dataInstalacao) {
        this.dataInstalacao = dataInstalacao;
    }

    public String getStatusCamera() {
        return statusCamera;
    }

    public void setStatusCamera(String statusCamera) {
        this.statusCamera = statusCamera;
    }
}