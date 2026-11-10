package br.com.fiap.citizentech.model.repository;

import br.com.fiap.citizentech.model.entity.Leitura;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LeituraRepository extends JpaRepository<Leitura, Long> {

    List<Leitura> findByStatusLeitura(String statusLeitura);

    List<Leitura> findByOperacaoIdOperacao(Long idOperacao);

    @Query("SELECT l FROM Leitura l " +
            "JOIN FETCH l.camera c " +
            "JOIN FETCH l.operacao o " +
            "JOIN FETCH o.veiculo " +
            "JOIN FETCH o.linha " +
            "ORDER BY l.dataHoraLeitura")
    List<Leitura> listarComRelacionamentos();

    Optional<Leitura> findById(Long id);
}