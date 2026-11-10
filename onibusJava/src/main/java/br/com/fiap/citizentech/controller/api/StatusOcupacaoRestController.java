package br.com.fiap.citizentech.controller.api;

import br.com.fiap.citizentech.model.dto.StatusOcupacaoDTO;
import br.com.fiap.citizentech.model.entity.FaixaOcupacao;
import br.com.fiap.citizentech.model.entity.Operacao;
import br.com.fiap.citizentech.model.repository.FaixaOcupacaoRepository;
import br.com.fiap.citizentech.model.repository.OperacaoRepository;
import br.com.fiap.citizentech.model.repository.StatusOcupacaoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/status")
public class StatusOcupacaoRestController {

    private final StatusOcupacaoRepository statusRepository;
    private final OperacaoRepository operacaoRepository;
    private final FaixaOcupacaoRepository faixaRepository;

    public StatusOcupacaoRestController(StatusOcupacaoRepository statusRepository,
                                        OperacaoRepository operacaoRepository,
                                        FaixaOcupacaoRepository faixaRepository) {
        this.statusRepository = statusRepository;
        this.operacaoRepository = operacaoRepository;
        this.faixaRepository = faixaRepository;
    }

    @GetMapping
    public List<StatusOcupacaoDTO> listar() {
        return statusRepository.findAll()
                .stream()
                .map(StatusOcupacaoDTO::new)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusOcupacaoDTO> buscar(@PathVariable Long id) {
        return statusRepository.findById(id)
                .map(s -> ResponseEntity.ok(new StatusOcupacaoDTO(s)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StatusOcupacaoDTO> criar(@RequestBody @Valid StatusOcupacaoDTO dto) {
        Operacao operacao = operacaoRepository.findById(dto.getIdOperacao()).orElse(null);
        FaixaOcupacao faixa = faixaRepository.findById(dto.getIdFaixa()).orElse(null);

        if (operacao == null || faixa == null) {
            return ResponseEntity.badRequest().build();
        }

        dto.setAtualizadoEm(java.time.LocalDateTime.now());
        var salvo = statusRepository.save(dto.toEntity(operacao, faixa));
        return ResponseEntity.status(HttpStatus.CREATED).body(new StatusOcupacaoDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusOcupacaoDTO> atualizar(@PathVariable Long id,
                                                       @RequestBody @Valid StatusOcupacaoDTO dto) {
        if (!statusRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Operacao operacao = operacaoRepository.findById(dto.getIdOperacao()).orElse(null);
        FaixaOcupacao faixa = faixaRepository.findById(dto.getIdFaixa()).orElse(null);

        if (operacao == null || faixa == null) {
            return ResponseEntity.badRequest().build();
        }

        dto.setIdStatus(id);
        dto.setAtualizadoEm(java.time.LocalDateTime.now());
        var salvo = statusRepository.save(dto.toEntity(operacao, faixa));
        return ResponseEntity.ok(new StatusOcupacaoDTO(salvo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!statusRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        statusRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}