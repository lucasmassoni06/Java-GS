package br.com.fiap.citizentech.model.repository;

import br.com.fiap.citizentech.model.entity.Linha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinhaRepository extends JpaRepository<Linha, Long> {
    java.util.List<Linha> findByAtiva(String ativa);
    java.util.List<Linha> findByNomeLinhaContainingIgnoreCase(String nome);
    java.util.Optional<Linha> findByCodigoLinha(String codigoLinha);
}