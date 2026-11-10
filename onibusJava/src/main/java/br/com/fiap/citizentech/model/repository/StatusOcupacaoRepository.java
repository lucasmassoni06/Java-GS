package br.com.fiap.citizentech.model.repository;

import br.com.fiap.citizentech.model.entity.StatusOcupacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StatusOcupacaoRepository extends JpaRepository<StatusOcupacao, Long> {

    List<StatusOcupacao> findByOperacaoIdOperacao(Long idOperacao);

    @Query("SELECT o.linha.codigoLinha, AVG(s.percentualOcupacao) " +
            "FROM StatusOcupacao s JOIN s.operacao o " +
            "GROUP BY o.linha.codigoLinha")
    List<Object[]> calcularMediaPorLinha();
}