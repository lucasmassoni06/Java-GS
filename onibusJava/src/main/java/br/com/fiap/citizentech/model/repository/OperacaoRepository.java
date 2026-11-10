package br.com.fiap.citizentech.model.repository;

import br.com.fiap.citizentech.model.entity.Operacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperacaoRepository extends JpaRepository<Operacao, Long> {
    java.util.List<Operacao> findByLinhaIdLinha(Long idLinha);
    java.util.List<Operacao> findByVeiculoIdVeiculo(Long idVeiculo);
}