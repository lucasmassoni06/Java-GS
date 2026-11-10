package br.com.fiap.citizentech.model.repository;

import br.com.fiap.citizentech.model.entity.FaixaOcupacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaixaOcupacaoRepository extends JpaRepository<FaixaOcupacao, Long> {
    java.util.List<FaixaOcupacao> findByNomeFaixa(String nomeFaixa);
}