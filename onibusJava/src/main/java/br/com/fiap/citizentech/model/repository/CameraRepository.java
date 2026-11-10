package br.com.fiap.citizentech.model.repository;

import br.com.fiap.citizentech.model.entity.Camera;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CameraRepository extends JpaRepository<Camera, Long> {
    java.util.List<Camera> findByVeiculoIdVeiculo(Long idVeiculo);
    java.util.List<Camera> findByStatusCamera(String statusCamera);
}