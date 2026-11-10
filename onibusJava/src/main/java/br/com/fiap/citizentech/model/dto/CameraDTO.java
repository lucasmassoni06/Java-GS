package br.com.fiap.citizentech.model.dto;

import br.com.fiap.citizentech.model.entity.Camera;
import br.com.fiap.citizentech.model.entity.Veiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CameraDTO {

    private Long idCamera;

    @NotNull(message = "O veiculo e obrigatorio")
    private Long idVeiculo;

    @NotBlank(message = "O nome da camera e obrigatorio")
    @Size(max = 60, message = "O nome deve ter no maximo 60 caracteres")
    private String nomeCamera;

    @Size(max = 40, message = "A posicao deve ter no maximo 40 caracteres")
    private String posicao;

    @Size(max = 20, message = "A resolucao deve ter no maximo 20 caracteres")
    private String resolucao;

    private LocalDate dataInstalacao;

    @Size(max = 15, message = "O status deve ter no maximo 15 caracteres")
    private String statusCamera;

    public CameraDTO() {
    }

    public CameraDTO(Camera camera) {
        this.idCamera = camera.getIdCamera();
        this.idVeiculo = camera.getVeiculo() != null ? camera.getVeiculo().getIdVeiculo() : null;
        this.nomeCamera = camera.getNomeCamera();
        this.posicao = camera.getPosicao();
        this.resolucao = camera.getResolucao();
        this.dataInstalacao = camera.getDataInstalacao();
        this.statusCamera = camera.getStatusCamera();
    }

    public Camera toEntity(Veiculo veiculo) {
        Camera camera = new Camera();
        camera.setIdCamera(this.idCamera);
        camera.setVeiculo(veiculo);
        camera.setNomeCamera(this.nomeCamera);
        camera.setPosicao(this.posicao);
        camera.setResolucao(this.resolucao);
        camera.setDataInstalacao(this.dataInstalacao);
        camera.setStatusCamera(this.statusCamera);
        return camera;
    }

    public Long getIdCamera() {
        return idCamera;
    }

    public void setIdCamera(Long idCamera) {
        this.idCamera = idCamera;
    }

    public Long getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Long idVeiculo) {
        this.idVeiculo = idVeiculo;
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