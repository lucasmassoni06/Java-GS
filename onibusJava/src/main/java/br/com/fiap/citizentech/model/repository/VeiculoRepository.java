package br.com.fiap.citizentech.model.repository;

import br.com.fiap.citizentech.model.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    java.util.Optional<Veiculo> findByPrefixoFrota(String prefixoFrota);
    java.util.List<Veiculo> findByModeloContainingIgnoreCase(String modelo);
}